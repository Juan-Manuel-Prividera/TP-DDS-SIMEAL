package ar.edu.utn.frba.dds.service.cargadatos;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.service.ColaboracionBuilder;
import ar.edu.utn.frba.dds.simeal.service.cargadorDatos.LectorCSV;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class LectorCSVTest {
    String csvFile = "src/main/java/ar/edu/utn/frba/dds/service/cargadorDatos/datos.csv";
    ColaboracionBuilder colaboracionFactory = new ColaboracionBuilder();

    @Test
    public void leerColaboradoresTest() throws IOException, CsvException {
        LectorCSV lectorCSV = new LectorCSV(csvFile);
       List<Colaboracion> colaboraciones = lectorCSV.leerColaboradores();

        Assertions.assertTrue(colaboraciones.size() == 4);
    }

}
