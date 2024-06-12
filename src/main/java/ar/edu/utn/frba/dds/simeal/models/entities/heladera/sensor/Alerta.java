package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
public class Alerta implements Incidente{
  LocalDateTime fechaYHora;
  TipoAlerta tipoAlerta;
  String descripcion;
  Heladera heladera;

  /*
  Quizás podría tener la medición asociada ?:
      La de temperatura que fue crítica,
      la de movimiento que siempre se reporta como incidente
      y la última medición que registró el sensor (si es alerta de conexión)
   */


}
