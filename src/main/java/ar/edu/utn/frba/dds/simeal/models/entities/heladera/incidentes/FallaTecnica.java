package ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class FallaTecnica implements Incidente{
  Heladera heladera;
  String descripcion;
  LocalDateTime fechaHora;
  Colaborador colaborador;
  String imagen;

  @Override
  public String getNotificacion() {
    DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    return "\t- Fecha: " + fechaHora.format(formatterDia)
        + "\n\t- Hora: " + fechaHora.format(formatterHora)
        + "\n\t- Colaborador: " + colaborador.getNombre() + " " + colaborador.getApellido()
        + "\n\t- Descripcion: " + descripcion;
  }
}
