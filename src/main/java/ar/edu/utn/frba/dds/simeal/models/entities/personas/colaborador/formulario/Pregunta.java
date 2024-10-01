package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pregunta")
public class Pregunta extends Persistente {
  @Column(name="pregunta")
  private String pregunta; // La pregunta de la pregunta O_o

  @Column(name="campo")
  private String param; // El param es el nombre técnico (postParams, .hbs, ...) de la pregunta.

  @Column(name="tipo")
  @Enumerated(EnumType.STRING)
  private TipoPregunta tipo;

  @ManyToMany
  @JoinTable(
          name = "pregunta_opcion",
          joinColumns = @JoinColumn( name="pregunta_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "opcion_id", referencedColumnName = "id"))
  private List<Opcion> opciones;

  @Column(name="required")
  private Boolean required;

}
