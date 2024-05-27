package ar.edu.utn.frba.dds.simeal.models.entities.formulario;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormularioContestado {
  private final List<Respuesta> respuestas;
  private LocalDate fechaRespuesta;


  public FormularioContestado(List<Respuesta> respuestas) {
    this.respuestas = respuestas;
  }
}
