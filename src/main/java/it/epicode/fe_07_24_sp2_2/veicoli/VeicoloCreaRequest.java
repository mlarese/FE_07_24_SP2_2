package it.epicode.fe_07_24_sp2_2.veicoli;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

// il frontend richiede di inviare solo certi dati per cui creo un DTO della richiesta
@Data
public class VeicoloCreaRequest {

    @NotEmpty(message = "Il campo marca non può essere vuoto")
    private String marca;

    @NotEmpty(message = "Il campo modello non può essere vuoto")
    private String modello;

    @NotEmpty(message = "Il campo targa non può essere vuoto")
    @Length(min = 4, max = 7, message = "La targa deve essere lunga 7 caratteri")
    private String targa;
}
