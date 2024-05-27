package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Medicion;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class Desperfecto {
  private String descripcion;
  private LocalDate fechaDeOcurrencia;
  private final Medicion comoSeDetecto;

  public Desperfecto(String descripcion, Medicion comoSeDetecto) {
    this.descripcion = descripcion;
    this.comoSeDetecto = comoSeDetecto;
    this.fechaDeOcurrencia = LocalDate.now();
  }
}
