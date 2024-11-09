package ar.edu.utn.frba.dds.simeal.models.dtos;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import com.mysql.cj.log.Log;
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
  private Boolean estaActiva;
  private String esElEncargado;

  public HeladeraDTO(Heladera heladera, Double ultimaTempRegistrada, Colaborador colaborador) {
    this.id = heladera.getId().toString();
    this.nombre = heladera.getNombre();
    this.latitud = heladera.getUbicacion().getCoordenada().getLatitud();
    this.longitud = heladera.getUbicacion().getCoordenada().getLongitud();
    this.nombreCalle = heladera.getUbicacion().getNombreCalle();
    this.altura = heladera.getUbicacion().getAltura();
    if (heladera.getActiva()){
      this.activa = "Activa :)";
      this.estaActiva = true;
    } else {
      this.activa = "Inactiva :(";
      this.estaActiva = null;
    }
    if (heladera.getColaboradorACargo() != null && colaborador.getId().equals(heladera.getColaboradorACargo().getId())) {
      Logger.debug("Es el encargado de: " + heladera.getNombre());
      this.esElEncargado = "true";
    } else {
      this.esElEncargado = "null";
    }
    this.ultimaTempRegistrada = ultimaTempRegistrada.toString();
  }
}
