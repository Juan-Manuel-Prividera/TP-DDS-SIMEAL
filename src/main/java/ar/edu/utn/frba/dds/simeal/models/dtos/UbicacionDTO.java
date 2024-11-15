package ar.edu.utn.frba.dds.simeal.models.dtos;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import javax.naming.ldap.PagedResultsControl;

@Getter
@Setter
public class UbicacionDTO {
  private String direccion;
  private Long id;

  public UbicacionDTO(Ubicacion ubicacion) {
    this.direccion = ubicacion.getStringUbi();
    this.id = ubicacion.getId();
  }
}
