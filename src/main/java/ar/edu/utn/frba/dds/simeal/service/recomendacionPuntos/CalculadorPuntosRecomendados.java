package ar.edu.utn.frba.dds.simeal.service.recomendacionPuntos;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.PuntosRecomendados;
import ar.edu.utn.frba.dds.simeal.service.recomendacionPuntos.ubicacionesapi.AdapterUbicacionesAPI;
import java.io.IOException;

public class CalculadorPuntosRecomendados {
  AdapterUbicacionesAPI adapterUbicacionesApi;

  public CalculadorPuntosRecomendados(AdapterUbicacionesAPI adapterUbicacionesApi) {
    this.adapterUbicacionesApi = adapterUbicacionesApi;
  }

  public PuntosRecomendados getPuntosRecomendados(double lat, double lon, double radio) throws IOException {
    return adapterUbicacionesApi.getPuntosRecomendados(lat, lon, radio);
  }
}
