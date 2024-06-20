package ar.edu.utn.frba.dds.simeal.service.recomendacionpuntos;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.PuntosRecomendados;
import ar.edu.utn.frba.dds.simeal.service.recomendacionpuntos.ubicacionesapi.AdapterUbicacionesApi;

import java.io.IOException;

public class CalculadorPuntosRecomendados {
  AdapterUbicacionesApi adapterUbicacionesApi;

  public CalculadorPuntosRecomendados(AdapterUbicacionesApi adapterUbicacionesApi) {
    this.adapterUbicacionesApi = adapterUbicacionesApi;
  }

  public PuntosRecomendados getPuntosRecomendados(double lat, double lon, double radio)
      throws IOException {
    return adapterUbicacionesApi.getPuntosRecomendados(lat, lon, radio);
  }
}
