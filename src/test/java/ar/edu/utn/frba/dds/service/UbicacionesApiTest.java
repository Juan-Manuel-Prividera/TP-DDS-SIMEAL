package ar.edu.utn.frba.dds.service;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.PuntosRecomendados;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.service.recomendacionPuntos.CalculadorPuntosRecomendados;
import ar.edu.utn.frba.dds.simeal.service.recomendacionPuntos.ubicacionesapi.AdapterCalculadorUbicaciones;
import ar.edu.utn.frba.dds.simeal.service.recomendacionPuntos.ubicacionesapi.AdapterUbicacionesAPI;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UbicacionesApiTest {
  AdapterUbicacionesAPI adapterCalculadorUbicaciones;
  CalculadorPuntosRecomendados calculadorPuntosRecomendados;
  Ubicacion ubicacion1;
  Ubicacion ubicacion2;

  @BeforeEach
  public void init() {
    adapterCalculadorUbicaciones = new AdapterCalculadorUbicaciones();
    ubicacion1 = new Ubicacion(29,20);
    ubicacion2 = new Ubicacion(29,38);

  }

  @Test @DisplayName("Probando directamente la API")
  public void apiUbicacionesTest() throws IOException {
    PuntosRecomendados puntosRecomendados;
    puntosRecomendados = adapterCalculadorUbicaciones.getPuntosRecomendados(12,32,5);

    Assertions.assertEquals(ubicacion1.getLat(),puntosRecomendados.getLocations().get(0).getLat());
    Assertions.assertEquals(ubicacion1.getLon(),puntosRecomendados.getLocations().get(0).getLon());
    Assertions.assertEquals(ubicacion2.getLat(),puntosRecomendados.getLocations().get(1).getLat());
    Assertions.assertEquals(ubicacion2.getLon(),puntosRecomendados.getLocations().get(1).getLon());

  }

  @Test @DisplayName("Test de la clase CalculadorPuntosRecomendados")
  public void calculadorPuntosRecomendadosTest() throws IOException {
    calculadorPuntosRecomendados = new CalculadorPuntosRecomendados(adapterCalculadorUbicaciones);
    PuntosRecomendados puntosRecomendados = calculadorPuntosRecomendados.getPuntosRecomendados(12,32,5);

    Assertions.assertEquals(ubicacion1.getLat(),puntosRecomendados.getLocations().get(0).getLat());
    Assertions.assertEquals(ubicacion1.getLon(),puntosRecomendados.getLocations().get(0).getLon());
    Assertions.assertEquals(ubicacion2.getLat(),puntosRecomendados.getLocations().get(1).getLat());
    Assertions.assertEquals(ubicacion2.getLon(),puntosRecomendados.getLocations().get(1).getLon());

  }


}
