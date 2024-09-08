package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Pregunta {
  @Column(name="pregunta")
  private String pregunta;
}
