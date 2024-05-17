package ar.edu.utn.frba.dds.service.ubicacionesapi;

import retrofit2.http.Query;
import retrofit2.http.GET;
import retrofit2.Call;

public interface UbicacionesServiceAPI {
  @GET
  Call<PuntosRecomendados> getUbicacionesRecomendadas(@Query("lat") float lat, @Query("lon") float lon, @Query("radio") float radio);
}
