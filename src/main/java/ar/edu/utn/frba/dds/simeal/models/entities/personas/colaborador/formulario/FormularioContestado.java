package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "formulario_contestado")
@NoArgsConstructor
public class FormularioContestado extends Persistente {
  @OneToMany
  @JoinColumn(name="formulario_contestado_id", referencedColumnName = "id")
  @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  private List<Respuesta> respuestas;
  @Column(name="fecha_de_respuesta")
  private LocalDate fechaRespuesta;

  @OneToOne
  private Formulario formulario;

  public FormularioContestado(List<Respuesta> respuestas, Formulario formulario) {
    this.respuestas = respuestas;
    this.formulario = formulario;
    fechaRespuesta = LocalDate.now();
  }
}
