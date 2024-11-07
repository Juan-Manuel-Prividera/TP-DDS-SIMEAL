package ar.edu.utn.frba.dds.simeal.models.dtos;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.EncargoTecnico;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class EncargoTecnicoDTO {
  private Long id;
  private String descripcionIncidente;
  private String nombreHeladera;
  private String ubicacionHeladera;
  private String aceptado;
  private String fechaHora;

  public EncargoTecnicoDTO(EncargoTecnico encargo) {
    this.id = encargo.getId();
    this.descripcionIncidente = encargo.getIncidente().getNotificacion();
    this.nombreHeladera = encargo.getIncidente().getHeladera().getNombre();
    this.ubicacionHeladera = encargo.getIncidente().getHeladera().getUbicacion().getStringUbi();
    if(encargo.getAceptado() == null) this.aceptado = "Pendiente";
    else if(encargo.getAceptado()) this.aceptado = "Aceptado";
    else this.aceptado = "Rechazado";

    this.fechaHora = encargo.getIncidente().getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
  }
}
