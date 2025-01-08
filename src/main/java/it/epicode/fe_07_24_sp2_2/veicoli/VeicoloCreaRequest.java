package it.epicode.fe_07_24_sp2_2.veicoli;

import lombok.Data;

// il frontend richiede di inviare solo certi dati per cui creo un DTO della richiesta
@Data
public class VeicoloCreaRequest {
    private String marca;
    private String modello;
    private String targa;
}
