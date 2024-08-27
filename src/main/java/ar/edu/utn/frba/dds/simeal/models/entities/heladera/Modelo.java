package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name="modelo_heladera")
@AllArgsConstructor
public class Modelo extends Persistente {
  @Column(name="temp_max")
  private final double temperaturaMax;

  @Column(name="temp_min")
  private final double temperaturaMin;

  @Column(name="capacidad_max")
  private final int capacidadMax;


}
