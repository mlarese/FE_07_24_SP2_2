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
        try{
            return ResponseEntity.ok( veicoloSrv.findById(id) );
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping
    // risponde a chiamate sull'endpoint  GET http://localhost:8080/api/veicoli
    public ResponseEntity<List<Veicolo>> listAllCars() {
        List<Veicolo> veicoli = veicoloSrv.findAll();

        return ResponseEntity.ok(veicoli);
    }

    @PostMapping
    public ResponseEntity<?> saveCar(@RequestBody VeicoloCreaRequest request ) {
        try{
            return new ResponseEntity<>(veicoloSrv.saveVeicolo(request), HttpStatus.CREATED);
        } catch (AlreadyExistsException e) {
             return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);

        }


    }
}
