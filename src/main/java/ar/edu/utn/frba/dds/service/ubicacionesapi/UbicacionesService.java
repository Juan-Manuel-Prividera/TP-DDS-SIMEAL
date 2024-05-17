package ar.edu.utn.frba.dds.service.ubicacionesapi;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UbicacionesService {
  private UbicacionesServiceAPI ubicacionesServiceAPI;
  private Retrofit retrofit;
  String urlApi; // Habira que sacarla de un archivo de configuracion pero como no tenemos la API...

  public UbicacionesService() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    this.ubicacionesServiceAPI = this.retrofit.create(UbicacionesServiceAPI.class);
  }

  public PuntosRecomendados getPuntosRecomendados(float lat, float lon, float radio) throws IOException {
    Call<PuntosRecomendados> requestPuntosRecomendados = ubicacionesServiceAPI.getUbicacionesRecomendadas(lat, lon, radio);
    Response<PuntosRecomendados> responsePuntosRescomendador = requestPuntosRecomendados.execute();

    return responsePuntosRescomendador.body();
  }
}
