package it.epicode.fe_07_24_sp2_2.veicoli;

import it.epicode.fe_07_24_sp2_2.exceptions.AlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeicoloSrv {
    private final VeicoloRepo veicoloRepo;

    // su richiesta del fe creo un metodo che restituisce tutti i veicoli
    public List<Veicolo> findAll() {
        return veicoloRepo.findAll();
    }

    // il FE ci richiede la possibilità di creare un veicolo inserendo solo targa modello e marca
    public Veicolo saveVeicolo(VeicoloCreaRequest request) {
        if(veicoloRepo.existsByTarga(request.getTarga())) {
            throw  new AlreadyExistsException("Un veicolo con questa targa esiste già");
        }

        Veicolo v = new Veicolo();
        BeanUtils.copyProperties(request,v);

        return veicoloRepo.save(v);
    }

    // frontend chiede di recuperare un auto tramite id
    public Veicolo findById(Long id) {
        if(!veicoloRepo.existsById(id)) {
            throw new EntityNotFoundException("Il veicolo non è stato trovato");
        }

        return veicoloRepo.findById(id).get();
    }

    public Veicolo modifyCar(Long id, Veicolo modVeicolo ){
        Veicolo v = findById(id);

        BeanUtils.copyProperties(modVeicolo, v);

        return veicoloRepo.save(v);


    }

    public Veicolo patchTarga(Long id, String targa){
        Veicolo v = findById(id);
        v.setTarga(targa);
        return veicoloRepo.save(v);
    }

    public Boolean deleteCar(Long id) {
        if(!veicoloRepo.existsById(id)) {
            throw new EntityNotFoundException("Il veicolo non è stato trovato");
        }

        veicoloRepo.deleteById(id);
        return true;
    }
}
