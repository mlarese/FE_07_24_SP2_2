package it.epicode.fe_07_24_sp2_2.veicoli;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class VeicoloPostConPage {
    Page<Veicolo> page;
    Veicolo lastVeicolo;
}
