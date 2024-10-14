package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pregunta")
public class Pregunta extends Persistente {
  @Column(name="pregunta")
  private String pregunta; // La pregunta de la pregunta O_o

  @Column(name="campo")
  private String param; // El param es el nombre técnico (postParams, .hb) s, ...de la pregunta.

  @Column(name="tipo")
  @Enumerated(EnumType.STRING)
  private TipoPregunta tipo;

  @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  @ManyToMany
  @JoinTable(
          name = "pregunta_opcion",
          joinColumns = @JoinColumn( name="pregunta_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "opcion_id", referencedColumnName = "id"))
  private List<Opcion> opciones;

  @Column(name="required")
  private Boolean required;

}
