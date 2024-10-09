package ar.edu.utn.frba.dds.service.broker;

import ar.edu.utn.frba.dds.simeal.service.RecomendacionDonacionAPIExterna.AdapterRecomendadorComunidad;
import ar.edu.utn.frba.dds.simeal.service.RecomendacionDonacionAPIExterna.Comunidad;
import ar.edu.utn.frba.dds.simeal.service.RecomendacionDonacionAPIExterna.RecomendacionComunidad;
import ar.edu.utn.frba.dds.simeal.service.RecomendacionDonacionAPIExterna.UbicacionesComunidadesRecomendadas;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

// No correr los dos a la vez
public class ApiComunidadesTest {
    AdapterRecomendadorComunidad adapter;

    @BeforeEach
    public void init(){
        adapter = new AdapterRecomendadorComunidad();
    }

    @Test
    public void ProbarApiComunidadesAdapter() throws IOException {
        UbicacionesComunidadesRecomendadas response
                = adapter.getUbicacionesComunidadesRecomendadas(
                        -34., -58., 14,3);

        Assertions.assertEquals( response.getLugares().size(), 3 );

        for (Comunidad comunidad : response.getLugares()) {
            System.out.println(comunidad.getNombre());
        }
    }

    @Test
    public void ProbarApiComunidades() throws IOException {
        RecomendacionComunidad recomendacionComunidad = new RecomendacionComunidad( adapter);
        List<Comunidad> response = recomendacionComunidad.obtenerComunidadesCercanas(-34., -58.);
        Assertions.assertEquals( response.size(), 5 );


    }
}
