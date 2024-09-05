package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="respuestas")
@NoArgsConstructor(force = true)
public class Respuesta extends Persistente {
  @Embedded
  @JoinColumn(name="pregunta_id")
  private final Pregunta pregunta;
  @Lob
  @Column(name="respuesta")
  private String respuesta;

  public Respuesta(Pregunta pregunta) {
    this.pregunta = pregunta;
  }
}
