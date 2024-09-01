package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "rubro")
@NoArgsConstructor
public class Rubro {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;
  @Column
  private String nombre;
  @ManyToOne
  @JoinColumn(name = "rubro_padre_id", referencedColumnName = "id")
  private Rubro rubroPadre;

  public void setRubroPadre(Rubro rubroPadre) {
    this.rubroPadre = rubroPadre;
  }

  public Rubro(String nombre, Rubro rubroPadre) {
    this.nombre = nombre;
    this.rubroPadre = rubroPadre;
  }
}
