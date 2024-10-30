package ar.edu.utn.frba.dds.service.calculadorCoordenadas;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Provincia;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.service.CalculadorCoordenadas.CalculadorCoordenadas;
import ar.edu.utn.frba.dds.simeal.service.CalculadorCoordenadas.CalculadorCoordenadasAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalcularCoordenadasTest {
  CalculadorCoordenadasAdapter calculadorCoordenadasAdapter;

  @BeforeEach
  public void init() {
    calculadorCoordenadasAdapter = new CalculadorCoordenadasAdapter();
  }

  @Test
  public void calcularCoordenadasConAdapter() {
    String callePrueba = "Frias";
    int altura = 1351;
    int cp = 1846;
    String provincia = "Buenos Aires";
    Coordenada coordenada = calculadorCoordenadasAdapter.calcularCoordenadas(callePrueba,altura, cp, provincia);
    System.out.println("Lat: " + coordenada.getLatitud() + " Long: " + coordenada.getLongitud());

    Assertions.assertEquals(-34.78804267755102, coordenada.getLatitud());
    Assertions.assertEquals(-58.38805301428572, coordenada.getLongitud());
  }

  @Test
  public void calcularCoordendasSinAdapter() {
    String callePrueba = "Frias";
    int altura = 1351;
    int cp = 1846;
    Provincia provincia = Provincia.Buenos_Aires;

    Ubicacion ubicacion = new Ubicacion(callePrueba,altura,provincia,cp,null);
    Coordenada coordenada = CalculadorCoordenadas.calcular(ubicacion);
    System.out.println("Lat: " + coordenada.getLatitud() + " Long: " + coordenada.getLongitud());

    Assertions.assertEquals(-34.78804267755102, coordenada.getLatitud());
    Assertions.assertEquals(-58.38805301428572, coordenada.getLongitud());
  }
}
