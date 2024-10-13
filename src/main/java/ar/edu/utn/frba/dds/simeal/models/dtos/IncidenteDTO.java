package ar.edu.utn.frba.dds.simeal.models.dtos;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.FallaTecnica;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidenteDTO {
  private String tipoIncidente;
  private String descripcion;
  private String fecha;
  private String hora;

  public IncidenteDTO(Alerta alerta) {
    this.tipoIncidente = "Alerta " + String.valueOf(alerta.getTipoAlerta()).toLowerCase().replace("alerta_", " ");
    this.descripcion = alerta.getDescripcion();
    this.fecha = String.valueOf(alerta.getFechaHora().toLocalDate());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    this.hora = String.valueOf(alerta.getFechaHora().toLocalTime().format(formatter));
  }
  public IncidenteDTO(FallaTecnica fallaTecnica) {
    this.tipoIncidente = "FallaTecnica";
    this.descripcion = fallaTecnica.getDescripcion();
    this.fecha = String.valueOf(fallaTecnica.getFechaHora().toLocalDate());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    this.hora = String.valueOf(fallaTecnica.getFechaHora().toLocalTime().format(formatter));
  }
}
