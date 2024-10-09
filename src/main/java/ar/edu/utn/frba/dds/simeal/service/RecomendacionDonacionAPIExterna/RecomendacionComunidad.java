package ar.edu.utn.frba.dds.simeal.service.RecomendacionDonacionAPIExterna;

import java.io.IOException;
import java.util.List;

public class RecomendacionComunidad {

    private AdapterRecomendadorComunidad adapter;

    public RecomendacionComunidad(AdapterRecomendadorComunidad adapter) {
        this.adapter = adapter;
    }

    public List<Comunidad> obtenerComunidadesCercanas(Double lat, Double lon) throws IOException {
        UbicacionesComunidadesRecomendadas response =
                adapter.getUbicacionesComunidadesRecomendadas(lat, lon, 13, 5);
        return response.getLugares();
    }

}
