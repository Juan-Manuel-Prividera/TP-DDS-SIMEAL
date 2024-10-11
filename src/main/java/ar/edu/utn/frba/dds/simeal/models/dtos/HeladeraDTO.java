package ar.edu.utn.frba.dds.simeal.models.dtos;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeladeraDTO {
  private String nombre;
  private Double latitud;
  private Double longitud;
  private String nombreCalle;
  private int altura;
  private Boolean activa;


  public HeladeraDTO(Heladera heladera) {
    this.nombre = heladera.getNombre();
    this.latitud = heladera.getUbicacion().getCoordenada().getLatitud();
    this.longitud = heladera.getUbicacion().getCoordenada().getLongitud();
    this.nombreCalle = heladera.getUbicacion().getNombreCalle();
    this.altura = heladera.getUbicacion().getAltura();
    this.activa = heladera.getActiva();
  }
}
