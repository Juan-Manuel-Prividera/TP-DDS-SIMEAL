package ar.edu.utn.frba.dds.simeal.models.entities;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Tarjeta;
import lombok.Getter;

import java.time.LocalDate;

public class Retiro {
  @Getter
  private final LocalDate fecha;
  private final Heladera heladera;
  private final Tarjeta tarjeta;

  public Retiro(Heladera heladera, Tarjeta tarjeta) {
    this.fecha = LocalDate.now();
    this.heladera = heladera;
    this.tarjeta = tarjeta;
  }
}
