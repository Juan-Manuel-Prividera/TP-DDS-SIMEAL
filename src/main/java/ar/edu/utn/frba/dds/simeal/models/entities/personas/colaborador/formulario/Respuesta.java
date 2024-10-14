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
  @JoinColumn(name = "pregunta_id", referencedColumnName = "id")
  @ManyToOne
  private Pregunta pregunta;
  @Lob
  @Column(name="respuesta")
  private String respuesta;

  @ManyToOne
  @JoinColumn(name="opcion", referencedColumnName = "id")
  private Opcion opcion;

  public Respuesta(Pregunta pregunta) {
    this.pregunta = pregunta;
  }


}
