package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;

import lombok.Getter;
import lombok.Setter;
import org.geotools.referencing.GeodeticCalculator;

@Getter
@Setter
public class Ubicacion {
  private String nombreCalle;
  private int altura;
  private Coordenada coordenada;
  private double condicionDeCercania = 1000; // 1000 metros

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

  public boolean equals(Ubicacion ubicacion) {
    return this.coordenada.getLongitud() == ubicacion.coordenada.getLongitud()
        && this.getCoordenada().getLatitud() == ubicacion.getCoordenada().getLatitud();
  }


  public double distanciaA(Ubicacion ubicacion) {
    double lat1 = this.getCoordenada().getLatitud();
    double lon1 = this.getCoordenada().getLongitud();
    double lat2 = ubicacion.getCoordenada().getLatitud();
    double lon2 = ubicacion.getCoordenada().getLongitud();

    GeodeticCalculator calculator = new GeodeticCalculator();
    calculator.setStartingGeographicPoint(lon1, lat1);
    calculator.setDestinationGeographicPoint(lon2, lat2);

    return calculator.getOrthodromicDistance(); // In meters

  }

  public String getStringUbi() {
    return this.nombreCalle + " " + this.altura;
  }

  public boolean estaCercaDe(Ubicacion ubicacion) {
    return this.distanciaA(ubicacion) <= this.condicionDeCercania;
  }
}
