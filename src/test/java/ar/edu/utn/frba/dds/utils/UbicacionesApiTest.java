package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.PuntosRecomendados;
import ar.edu.utn.frba.dds.simeal.service.recomendacionpuntos.CalculadorPuntosRecomendados;
import ar.edu.utn.frba.dds.simeal.service.recomendacionpuntos.ubicacionesapi.AdapterCalculadorUbicaciones;
import ar.edu.utn.frba.dds.simeal.service.recomendacionpuntos.ubicacionesapi.AdapterUbicacionesApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class UbicacionesApiTest {
  AdapterUbicacionesApi adapterCalculadorUbicaciones;
  CalculadorPuntosRecomendados calculadorPuntosRecomendados;
  Coordenada punto1;
  Coordenada punto2;

  @BeforeEach
  public void init() {
    adapterCalculadorUbicaciones = new AdapterCalculadorUbicaciones();
    punto1 = new Coordenada(20,29);
    punto2 = new Coordenada(38,29);

  }

  @Test @DisplayName("Probando directamente la API")
  public void apiUbicacionesTest() throws IOException {
    PuntosRecomendados puntosRecomendados;
    puntosRecomendados = adapterCalculadorUbicaciones.getPuntosRecomendados(12,32,5);

    Assertions.assertEquals(punto1.getLatitud(),puntosRecomendados.getLocations().get(0).getLatitud());
    Assertions.assertEquals(punto1.getLongitud(),puntosRecomendados.getLocations().get(0).getLongitud());
    Assertions.assertEquals(punto2.getLatitud(),puntosRecomendados.getLocations().get(1).getLatitud());
    Assertions.assertEquals(punto2.getLongitud(),puntosRecomendados.getLocations().get(1).getLongitud());

  }

  @Test @DisplayName("Test de la clase CalculadorPuntosRecomendados")
  public void calculadorPuntosRecomendadosTest() throws IOException {
    calculadorPuntosRecomendados = new CalculadorPuntosRecomendados(adapterCalculadorUbicaciones);
    PuntosRecomendados puntosRecomendados = calculadorPuntosRecomendados.getPuntosRecomendados(12,32,5);

    Assertions.assertEquals(punto1.getLatitud(),puntosRecomendados.getLocations().get(0).getLatitud());
    Assertions.assertEquals(punto1.getLongitud(),puntosRecomendados.getLocations().get(0).getLongitud());
    Assertions.assertEquals(punto2.getLatitud(),puntosRecomendados.getLocations().get(1).getLatitud());
    Assertions.assertEquals(punto2.getLongitud(),puntosRecomendados.getLocations().get(1).getLongitud());


  }


}
