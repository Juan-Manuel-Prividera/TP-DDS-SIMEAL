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
  private String id;
  private String nombre;
  private Double latitud;
  private Double longitud;
  private String nombreCalle;
  private int altura;
  private String activa;
  private String ultimaTempRegistrada;


  public HeladeraDTO(Heladera heladera, Double ultimaTempRegistrada) {
    this.id = heladera.getId().toString();
    this.nombre = heladera.getNombre();
    this.latitud = heladera.getUbicacion().getCoordenada().getLatitud();
    this.longitud = heladera.getUbicacion().getCoordenada().getLongitud();
    this.nombreCalle = heladera.getUbicacion().getNombreCalle();
    this.altura = heladera.getUbicacion().getAltura();
    if (heladera.getActiva()){
      this.activa = "Activa :)";
    } else
      this.activa = "Inactiva :(";

    this.ultimaTempRegistrada = ultimaTempRegistrada.toString();
  }
}
