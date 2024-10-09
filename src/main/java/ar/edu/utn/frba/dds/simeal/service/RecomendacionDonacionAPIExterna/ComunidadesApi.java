package ar.edu.utn.frba.dds.simeal.service.RecomendacionDonacionAPIExterna;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ComunidadesApi {
    @GET("/api/locaciones-donacion")
    Call<UbicacionesComunidadesRecomendadas> locacionesDonacion(@Query("lat") double lat,
                                                                @Query("lon") double lon,
                                                                @Query("limite") int limite,
                                                                @Query("distanciaMaximaKM") double distanciaMaxima,
                                                                @Header("Authorization") String authorization
    );

    @GET("/api/key")
    Call<ApiComunidadesKey> apiKey();

}
