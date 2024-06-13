package ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class Alerta implements Incidente{
  Heladera heladera;
  String descripcion;
  LocalDateTime fechaHora;
  TipoAlerta tipoAlerta;
  @Override
  public String getNotificacion() {
    DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    return "\t- Fecha: " + fechaHora.format(formatterDia)
        + "\n\t- Hora: " + fechaHora.format(formatterHora)
        + "\n\t- Alerta de tipo: " + tipoAlerta.name()
        + "\n\t- Descripción: " + descripcion;
  }
}


  /*
  Quizás podría tener la medición asociada ?:
      La de temperatura que fue crítica,
      la de movimiento que siempre se reporta como incidente
      y la última medición que registró el sensor (si es alerta de conexión)
   */

