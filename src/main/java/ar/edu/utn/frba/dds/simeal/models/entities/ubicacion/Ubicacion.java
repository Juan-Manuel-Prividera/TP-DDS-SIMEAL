package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.service.CalculadorCoordenadas.CalculadorCoordenadas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.geotools.referencing.GeodeticCalculator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="ubicacion")
@NoArgsConstructor
@AllArgsConstructor
public class Ubicacion extends Persistente {
  @Column(name = "nombre_calle")
  private String nombreCalle;

  @Column(name = "altura")
  private int altura;

  @Column(name = "provincia")
  @Enumerated(EnumType.STRING)
  private Provincia provincia;

  @Column(name = "codigo_postal")
  private int codigoPostal;

  @Embedded
  private Coordenada coordenada;

  public Ubicacion(double longitud, double latitud) {
    this.coordenada = new Coordenada(longitud, latitud);
  }

  public Ubicacion(String nombreCalle, int altura, Provincia provincia, int codigoPostal) {
    this.nombreCalle = nombreCalle;
    this.altura = altura;
    this.provincia = provincia;
    this.codigoPostal = codigoPostal;
    this.coordenada = CalculadorCoordenadas.calcular(this);
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

  public boolean estaCercaDe(Ubicacion ubicacion, int condicionDeCercania) {
    return this.distanciaA(ubicacion) <= condicionDeCercania;
  }
}
