package it.epicode.fe_07_24_sp2_2.veicoli;

import it.epicode.fe_07_24_sp2_2.exceptions.AlreadyExistsException;
import it.epicode.fe_07_24_sp2_2.pdf.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veicoli")

// risponde a chiamate sull'endpoint http://localhost:8080/api/veicoli
public class VeicoloController {
    @Autowired
    private VeicoloSrv veicoloSrv;
    @Autowired
    private EmailSenderService emailSenderService;

    // dati paginati
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Veicolo>> findAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(veicoloSrv.findAll(pageable));
    }
    // risponde a chiamate sull'endpoint GET http://localhost:8080/api/veicoli/1
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
            return ResponseEntity.ok( veicoloSrv.findById(id) );
    }
    @GetMapping
    // risponde a chiamate sull'endpoint  GET http://localhost:8080/api/veicoli
    public ResponseEntity<Page<Veicolo>> listAllCars(@ParameterObject  Pageable pageable) {

        return ResponseEntity.ok(veicoloSrv.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Veicolo> saveCar(@RequestBody @Valid VeicoloCreaRequest request ) {

        // invio di un email semplice
        /*
        emailSenderService.sendEmail(
                    "mauro.larese@gmail.com",
                    "Nuovo veicolo inserito",
                    "Caro Andrea è stato inserito un nuovo veicolo");

        */

        try {
            // invio email con html
            emailSenderService.sendEmail(
                    "mauro.larese@gmail.com",
                    "Nuovo veicolo inserito",
                    "<b>Caro Andrea</b> è stato inserito un nuovo veicolo");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(veicoloSrv.saveVeicolo(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    // risponde a chiamate sull'endpoint  PUT http://localhost:8080/api/veicoli/1
    public ResponseEntity<?> modifyCar(@PathVariable Long id, @RequestBody Veicolo modVeicolo) {

            return ResponseEntity.ok(veicoloSrv.modifyCar(id, modVeicolo));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchTarga(@PathVariable Long id, @RequestBody String targa){

            return ResponseEntity.ok(veicoloSrv.patchTarga(id, targa));

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCar( @PathVariable Long id) {
        veicoloSrv.deleteCar(id);
        return new ResponseEntity<>("Veicolo eliminato", HttpStatus.NO_CONTENT);
    }

}
