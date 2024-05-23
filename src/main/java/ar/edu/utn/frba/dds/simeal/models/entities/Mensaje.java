package ar.edu.utn.frba.dds.simeal.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Mensaje {
    private String mensaje;
    private String asunto;

    public Mensaje(String mensaje, String asunto) {
        this.mensaje = mensaje;
        this.asunto = asunto;
    }
}
