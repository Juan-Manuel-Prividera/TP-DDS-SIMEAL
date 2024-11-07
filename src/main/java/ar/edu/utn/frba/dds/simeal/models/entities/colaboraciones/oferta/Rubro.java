package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "rubro")
@NoArgsConstructor
public class Rubro extends Persistente {
  @Column
  private String nombre;

  @ManyToOne
  @JoinColumn(name = "rubro_padre_id", referencedColumnName = "id")
  @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  @Setter
  private Rubro rubroPadre;

  public Rubro(String nombre, Rubro rubroPadre) {
    this.nombre = nombre;
    this.rubroPadre = rubroPadre;
  }
  public Rubro(String nombre) {
    this.nombre = nombre;
  }
}
