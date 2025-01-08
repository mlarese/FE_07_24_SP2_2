package it.epicode.fe_07_24_sp2_2.veicoli;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VeicoloRepo extends JpaRepository<Veicolo, Long> {
    boolean existsByTarga(String targa);
}
