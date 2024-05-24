package ar.edu.utn.frba.dds.simeal.service.recomendacionPuntos;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.PuntosRecomendados;
import ar.edu.utn.frba.dds.simeal.service.recomendacionPuntos.ubicacionesapi.AdapterUbicacionesAPI;

public class CalculadorPuntosRecomendados {
  AdapterUbicacionesAPI adapterUbicacionesApi;

  public CalculadorPuntosRecomendados(AdapterUbicacionesAPI adapterUbicacionesApi) {
    this.adapterUbicacionesApi = adapterUbicacionesApi;
  }

  public PuntosRecomendados getPuntosRecomendados(double lat, double lon, double radio) {
    return adapterUbicacionesApi.getUbicaciones(lat, lon, radio);
  }
}
