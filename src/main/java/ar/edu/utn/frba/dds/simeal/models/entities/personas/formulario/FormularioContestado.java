package ar.edu.utn.frba.dds.simeal.models.entities.personas.formulario;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FormularioContestado {
  private final List<Respuesta> respuestas;
  private LocalDate fechaRespuesta;


  public FormularioContestado(List<Respuesta> respuestas) {
    this.respuestas = respuestas;
  }
}
