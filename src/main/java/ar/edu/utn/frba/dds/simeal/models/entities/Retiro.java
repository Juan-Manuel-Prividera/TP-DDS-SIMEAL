package ar.edu.utn.frba.dds.simeal.models.entities;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Tarjeta;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@AllArgsConstructor
public class Retiro {
  private LocalDate fechaRetiro;
  private Heladera heladera;
  private Tarjeta tarjeta;
}
