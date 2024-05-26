package ar.edu.utn.frba.dds.simeal.service.recomendacionPuntos.ubicacionesapi;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.PuntosRecomendados;
import java.io.IOException;

public interface AdapterUbicacionesAPI {
  // Esta seria la interfaz que conoce nuestro sistema
  PuntosRecomendados getPuntosRecomendados(double lat, double lon, double radio) throws IOException;
}
