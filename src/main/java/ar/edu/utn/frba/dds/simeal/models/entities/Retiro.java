package ar.edu.utn.frba.dds.simeal.models.entities;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.TarjetaPersonaVulnerable;
import lombok.Getter;

import java.time.LocalDate;

public class Retiro {
  @Getter
  private final LocalDate fecha;
  private final Heladera heladera;
  private final TarjetaPersonaVulnerable tarjeta;

  public Retiro(Heladera heladera, TarjetaPersonaVulnerable tarjeta) {
    this.fecha = LocalDate.now();
    this.heladera = heladera;
    this.tarjeta = tarjeta;
  }
}
