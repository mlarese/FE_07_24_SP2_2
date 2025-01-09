package it.epicode.fe_07_24_sp2_2.veicoli;

import it.epicode.fe_07_24_sp2_2.exceptions.AlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class VeicoloSrv {
    private final VeicoloRepo veicoloRepo;

    // risposta a domanda di andrea di come fare una post con restituzione dei risultati paginati
    // rachiude la risposta in un oggetto che contiene sia l'ultimo veicolo inserito che la pagina
   public VeicoloPostConPage insertAndGivePage( VeicoloCreaRequest request, Pageable pageable ) {
      VeicoloPostConPage vpp = new VeicoloPostConPage();

      Veicolo salvato = saveVeicolo(request);
      vpp.setLastVeicolo(salvato);
      vpp.setPage(  findAll(pageable)  );

      return vpp;
   }

   public Page<Veicolo> findAll(Pageable pageable) {
        return veicoloRepo.findAll(pageable);
   }
    // su richiesta del fe creo un metodo che restituisce tutti i veicoli
    public List<Veicolo> findAll() {
        return veicoloRepo.findAll();
    }

    // il FE ci richiede la possibilità di creare un veicolo inserendo solo targa modello e marca
    public Veicolo saveVeicolo(@Valid VeicoloCreaRequest request) {
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
