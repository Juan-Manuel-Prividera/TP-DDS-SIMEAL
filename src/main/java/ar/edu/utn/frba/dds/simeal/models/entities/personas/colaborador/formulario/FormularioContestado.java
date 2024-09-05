package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FormularioContestado extends Persistente {
  @Column(name="respuestas")
  private final List<Respuesta> respuestas;
  @Column(name="fecha_de_respuesta")
  private LocalDate fechaRespuesta;


  public FormularioContestado(List<Respuesta> respuestas) {
    this.respuestas = respuestas;
  }
}
