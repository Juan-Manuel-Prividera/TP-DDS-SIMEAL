package ar.edu.utn.frba.dds.simeal.models.entities;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Tarjeta;
import java.time.LocalDate;
import lombok.Getter;

public class Retiro {
  @Getter
  private final LocalDate fechaRetiro;
  private final Heladera heladera;
  private final Tarjeta tarjeta;

  public Retiro(Heladera heladera, Tarjeta tarjeta) {
    this.fechaRetiro = LocalDate.now();
    this.heladera = heladera;
    this.tarjeta = tarjeta;
  }
}
