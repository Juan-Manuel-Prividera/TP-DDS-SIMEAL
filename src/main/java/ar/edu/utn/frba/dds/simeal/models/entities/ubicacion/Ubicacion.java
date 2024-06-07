package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ubicacion {
  private String nombreCalle;
  private int altura;
  private Coordenada coordenada;

  public Ubicacion(double longitud, double latitud) {
    this.coordenada = new Coordenada(longitud, latitud);
  }

  public Ubicacion(String nombreCalle, int altura) {
    this.nombreCalle = nombreCalle;
    this.altura = altura;
  }

  public Ubicacion(String nombreCalle, int altura, double longitud, double latitud) {
    this.nombreCalle = nombreCalle;
    this.altura = altura;
    this.coordenada = new Coordenada(longitud, latitud);
  }

}
