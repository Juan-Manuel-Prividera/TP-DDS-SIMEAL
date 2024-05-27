package ar.edu.utn.frba.dds.simeal.service.recomendacionpuntos.ubicacionesapi;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.PuntosRecomendados;
import java.io.IOException;

public interface AdapterUbicacionesApi {
  // Esta seria la interfaz que conoce nuestro sistema
  PuntosRecomendados getPuntosRecomendados(double lat, double lon, double radio) throws IOException;
}
