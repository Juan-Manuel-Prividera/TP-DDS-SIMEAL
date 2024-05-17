package ar.edu.utn.frba.dds.service.cargadatos;

import ar.edu.utn.frba.dds.domain.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.domain.personas.Colaborador;
import ar.edu.utn.frba.dds.domain.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.domain.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.domain.personas.medioContacto.Email;
import ar.edu.utn.frba.dds.domain.personas.medioContacto.MedioContacto;
import ar.edu.utn.frba.dds.service.cargadorDatos.ColaboracionFactory;
import ar.edu.utn.frba.dds.service.cargadorDatos.LectorCSV;
import ar.edu.utn.frba.dds.service.cargadorDatos.TipoColaboracion;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class LectorCSVTest {
    String csvFile = "src/main/java/ar/edu/utn/frba/dds/service/cargadorDatos/datos.csv";
    ColaboracionFactory colaboracionFactory = new ColaboracionFactory();

    @Test
    public void leerColaboradoresTest() throws IOException, CsvException {
        LectorCSV lectorCSV = new LectorCSV(csvFile);
       List<Colaboracion> colaboraciones = lectorCSV.leerColaboradores();

        Assertions.assertTrue(colaboraciones.size() == 4);
    }

}
