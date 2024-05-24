package ar.edu.utn.frba.dds.simeal.models.entities.formulario;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Respuesta {
  private Pregunta pregunta;
  private String respuesta;
}
