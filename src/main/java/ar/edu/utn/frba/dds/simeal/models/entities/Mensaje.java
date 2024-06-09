package ar.edu.utn.frba.dds.simeal.models.entities;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mensaje {
  private String mensaje;
  private String asunto;
  private LocalDateTime fechaEnvio;

  public Mensaje(String mensaje, String asunto) {
    this.mensaje = mensaje;
    this.asunto = asunto;
  }
}
