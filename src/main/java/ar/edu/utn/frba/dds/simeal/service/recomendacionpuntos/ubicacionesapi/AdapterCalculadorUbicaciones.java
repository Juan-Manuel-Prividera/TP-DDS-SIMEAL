package ar.edu.utn.frba.dds.simeal.service.recomendacionpuntos.ubicacionesapi;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.PuntosRecomendados;
import ar.edu.utn.frba.dds.simeal.service.ConfigReader;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdapterCalculadorUbicaciones implements AdapterUbicacionesApi {
  private UbicacionesApi ubicacionesApi;
  private Retrofit retrofit;
  String urlApi;
  ConfigReader configReader;

  public AdapterCalculadorUbicaciones() {
    configReader = new ConfigReader();
    this.urlApi = configReader.getProperty("url.api");

    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public PuntosRecomendados getPuntosRecomendados(double lat, double lon, double radio)
      throws IOException {

    ubicacionesApi = retrofit.create(UbicacionesApi.class);
    Call<PuntosRecomendados> requestPuntosRecomendados;
    requestPuntosRecomendados = ubicacionesApi.ubicaciones(lat, lon, radio);
    Response<PuntosRecomendados> responsePuntosRescomendador = requestPuntosRecomendados.execute();
    return responsePuntosRescomendador.body();

  }
}
