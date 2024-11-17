package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "localidad")
public class Localidad extends Persistente {
  @Column(name = "localidad")
  private String nombre;

  @Column(name = "provincia")
  @Enumerated(EnumType.STRING)
  private Provincia provincia;

  public Localidad(String nombre, Provincia provincia) {
    this.nombre = nombre;
    this.provincia = provincia;
  }
}
