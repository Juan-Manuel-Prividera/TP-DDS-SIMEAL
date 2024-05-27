package ar.edu.utn.frba.dds.simeal.service.recomendacionpuntos.ubicacionesapi;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.PuntosRecomendados;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UbicacionesApi {
  // Esta seria la solicitud a la API posta
  @GET("ubicaciones")
  Call<PuntosRecomendados> ubicaciones(@Query("lat") double lat, @Query("lon") double lon,
                                       @Query("radio") double radio);
}
