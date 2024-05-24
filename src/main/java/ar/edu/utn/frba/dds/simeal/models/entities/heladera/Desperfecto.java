package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Medicion;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Desperfecto {
  private String descripcion;
  private LocalDate fechaDeOcurrencia;
  private Medicion comoSeDetecto;

  public Desperfecto(String descripcion, Medicion comoSeDetecto) {
    this.descripcion = descripcion;
    this.comoSeDetecto = comoSeDetecto;
    this.fechaDeOcurrencia = LocalDate.now();
  }
}
