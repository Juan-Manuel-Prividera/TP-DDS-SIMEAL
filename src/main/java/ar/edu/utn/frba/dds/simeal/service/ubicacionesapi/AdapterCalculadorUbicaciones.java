package ar.edu.utn.frba.dds.simeal.service.ubicacionesapi;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class AdapterCalculadorUbicaciones {
  private UbicacionesAPI ubicacionesServiceAPI;
  private Retrofit retrofit;
  String urlApi; // Habira que sacarla de un archivo de configuracion pero como no tenemos la API...

  public AdapterCalculadorUbicaciones() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    this.ubicacionesServiceAPI = this.retrofit.create(UbicacionesAPI.class);
  }

  public PuntosRecomendados getPuntosRecomendados(float lat, float lon, float radio) throws IOException {
    Call<PuntosRecomendados> requestPuntosRecomendados = ubicacionesServiceAPI.getPuntosRecomendadas(lat, lon, radio);
    Response<PuntosRecomendados> responsePuntosRescomendador = requestPuntosRecomendados.execute();

    return responsePuntosRescomendador.body();
  }
}
