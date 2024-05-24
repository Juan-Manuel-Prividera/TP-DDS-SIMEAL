package ar.edu.utn.frba.dds.simeal.service.recomendacionPuntos.ubicacionesapi;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.PuntosRecomendados;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UbicacionesApi {
  // Esta seria la solicitud a la API posta
  @GET
  Call<PuntosRecomendados> getPuntosRecomendadas(@Query("lat") float lat, @Query("lon") float lon,
                                                 @Query("radio") float radio);
}
