package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="respuestas")
@NoArgsConstructor(force = true)
public class Respuesta extends Persistente {
  @Embedded
  @Column(name = "pregunta")
  private final Pregunta pregunta;
  @Lob
  @Column(name="respuesta")
  private String respuesta;

  public Respuesta(Pregunta pregunta) {
    this.pregunta = pregunta;
  }
}
