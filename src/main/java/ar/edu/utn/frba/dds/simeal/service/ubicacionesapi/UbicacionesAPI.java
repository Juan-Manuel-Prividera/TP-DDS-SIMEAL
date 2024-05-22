package ar.edu.utn.frba.dds.simeal.service.ubicacionesapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UbicacionesAPI {
  @GET
  Call<PuntosRecomendados> getPuntosRecomendadas(@Query("lat") float lat, @Query("lon") float lon, @Query("radio") float radio);
}
