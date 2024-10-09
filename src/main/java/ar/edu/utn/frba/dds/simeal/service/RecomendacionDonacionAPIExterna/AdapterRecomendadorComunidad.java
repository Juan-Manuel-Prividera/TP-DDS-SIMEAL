package ar.edu.utn.frba.dds.simeal.service.RecomendacionDonacionAPIExterna;

import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class AdapterRecomendadorComunidad {
    private ComunidadesApi comunidadesApi;
    private Retrofit retrofit = null;
    String urlApi;
    ConfigReader configReader;

    // Comerse la API, ehm consumir API
    public AdapterRecomendadorComunidad() {
        configReader = new ConfigReader();
        this.urlApi = configReader.getProperty("api.comunidades.url");

        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        comunidadesApi = retrofit.create(ComunidadesApi.class);
    }

    public UbicacionesComunidadesRecomendadas getUbicacionesComunidadesRecomendadas(
            double lat, double lon, double distanciaMaximaKM, int limite)
            throws IOException {

        Call<UbicacionesComunidadesRecomendadas> request;
        request = comunidadesApi.locacionesDonacion(lat, lon, limite, distanciaMaximaKM, this.GetApiKey());
        Response<UbicacionesComunidadesRecomendadas> response = request.execute();
        return response.body();
    }

    private String GetApiKey() throws IOException {
        Call<ApiComunidadesKey> request;
        request = comunidadesApi.apiKey();
        Response<ApiComunidadesKey> response = request.execute();

        String message = response.body().getMensaje();
        String key = response.body().getKey();
        System.out.println("Mensaje: " + message);

        return key;

    }

}
