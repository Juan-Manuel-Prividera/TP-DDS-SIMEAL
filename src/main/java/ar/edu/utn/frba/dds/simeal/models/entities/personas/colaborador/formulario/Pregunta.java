package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Pregunta {
  @Column(name="pregunta")
  private String pregunta;
}
