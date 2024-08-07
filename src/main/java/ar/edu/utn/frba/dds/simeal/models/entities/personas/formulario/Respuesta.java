package ar.edu.utn.frba.dds.simeal.models.entities.personas.formulario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Respuesta {
  private final Pregunta pregunta;
  private String respuesta;

  public Respuesta(Pregunta pregunta) {
    this.pregunta = pregunta;
  }
}
