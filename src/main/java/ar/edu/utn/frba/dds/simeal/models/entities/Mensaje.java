package ar.edu.utn.frba.dds.simeal.models.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Mensaje {
  private String mensaje;
  private String asunto;
  private LocalDateTime fechaEnvio;

  public Mensaje(String mensaje, String asunto) {
    this.mensaje = mensaje;
    this.asunto = asunto;
    this.fechaEnvio = LocalDateTime.now();
  }
  public Mensaje(String mensaje) {
    this.mensaje = mensaje;
    this.fechaEnvio = LocalDateTime.now();
  }
}
