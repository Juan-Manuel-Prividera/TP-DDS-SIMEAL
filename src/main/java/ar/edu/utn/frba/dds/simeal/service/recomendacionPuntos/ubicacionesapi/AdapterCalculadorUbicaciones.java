package ar.edu.utn.frba.dds.simeal.service.recomendacionPuntos.ubicacionesapi;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.PuntosRecomendados;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdapterCalculadorUbicaciones {
  private UbicacionesApi ubicacionesServiceApi;
  private Retrofit retrofit;
  String urlApi; // Habira que sacarla de un archivo de configuracion pero como no tenemos la API...

  public AdapterCalculadorUbicaciones() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    this.ubicacionesServiceApi = this.retrofit.create(UbicacionesApi.class);
  }

  public PuntosRecomendados getPuntosRecomendados(float lat, float lon, float radio)
      throws IOException {
    Call<PuntosRecomendados> requestPuntosRecomendados = ubicacionesServiceApi
        .getPuntosRecomendadas(lat, lon, radio);
    Response<PuntosRecomendados> responsePuntosRescomendador = requestPuntosRecomendados.execute();

    return responsePuntosRescomendador.body();
  }
}
