package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name="modelo_heladera")
@AllArgsConstructor
@NoArgsConstructor
public class ModeloHeladera extends Persistente {
  @Column(name="nombre")
  private String nombre;

  @Column(name="temp_max")
  private double temperaturaMax;

  @Column(name="temp_min")
  private double temperaturaMin;

  @Column(name="capacidad_max")
  private int capacidadMax;


}
