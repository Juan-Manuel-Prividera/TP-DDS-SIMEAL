package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ubicacion {
  private String nombreCalle;
  private int altura;
  private double longitud;
  private double latitud;

  public Ubicacion(double longitud, double latitud) {
    this.longitud = longitud;
    this.latitud = latitud;
  }

  public Ubicacion(String nombreCalle, int altura) {
    this.nombreCalle = nombreCalle;
    this.altura = altura;
  }

  public Ubicacion(String nombreCalle, int altura, double longitud, double latitud) {
    this.nombreCalle = nombreCalle;
    this.altura = altura;
    this.longitud = longitud;
    this.latitud = latitud;
  }
}
