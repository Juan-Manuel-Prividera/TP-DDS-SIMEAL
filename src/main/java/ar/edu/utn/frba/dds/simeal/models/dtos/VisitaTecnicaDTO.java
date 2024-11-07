package ar.edu.utn.frba.dds.simeal.models.dtos;


import ar.edu.utn.frba.dds.simeal.models.entities.heladera.VisitaTecnica;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class VisitaTecnicaDTO {
  private Long id;
  private Long heladeraId;
  private String nombreHeladera;
  private String descripcion;
  private String fechaHora;
  private Boolean exitosa;

  public VisitaTecnicaDTO(VisitaTecnica visitaTecnica) {
    this.id = visitaTecnica.getId();
    this.heladeraId = visitaTecnica.getHeladera().getId();
    this.nombreHeladera = visitaTecnica.getHeladera().getNombre();
    this.descripcion = visitaTecnica.getDescripcion();
    this.fechaHora = visitaTecnica.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    this.exitosa = visitaTecnica.getExitosa();
  }
}
