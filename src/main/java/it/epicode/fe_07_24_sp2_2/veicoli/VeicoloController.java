package it.epicode.fe_07_24_sp2_2.veicoli;

import it.epicode.fe_07_24_sp2_2.exceptions.AlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veicoli")

// risponde a chiamate sull'endpoint http://localhost:8080/api/veicoli
public class VeicoloController {
    @Autowired
    private VeicoloSrv veicoloSrv;

    // risponde a chiamate sull'endpoint GET http://localhost:8080/api/veicoli/1
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
            return ResponseEntity.ok( veicoloSrv.findById(id) );
    }
    @GetMapping
    // risponde a chiamate sull'endpoint  GET http://localhost:8080/api/veicoli
    public ResponseEntity<List<Veicolo>> listAllCars() {
        List<Veicolo> veicoli = veicoloSrv.findAll();

        return ResponseEntity.ok(veicoli);
    }

    @PostMapping
    public ResponseEntity<Veicolo> saveCar(@RequestBody VeicoloCreaRequest request ) {
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
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        veicoloSrv.deleteCar(id);
        return new ResponseEntity<>("Veicolo eliminato", HttpStatus.NO_CONTENT);
    }

}
