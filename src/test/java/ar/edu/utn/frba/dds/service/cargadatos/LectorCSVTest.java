package ar.edu.utn.frba.dds.service.cargadatos;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.service.ColaboracionBuilder;
import ar.edu.utn.frba.dds.simeal.service.cargadordatos.LectorCsv;
import com.opencsv.exceptions.CsvException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LectorCSVTest {
    String csvFile = "src/main/java/ar/edu/utn/frba/dds/simeal/service/cargadordatos/datos.csv";
    LectorCsv lectorCSV;
    List<ColaboracionPuntuable> colaboraciones;
    ColaboracionPuntuable colaboracionPuntuablePrueba;
    ColaboracionBuilder colaboracionBuilder;
    Colaborador colaboradorPrueba;


    @BeforeEach
    public void init() throws FileNotFoundException {
        lectorCSV = new LectorCsv(csvFile);
        colaboraciones = new ArrayList<>();
        colaboracionBuilder = new ColaboracionBuilder();

        colaboradorPrueba = new Colaborador(new Documento(TipoDocumento.DNI,"01234567"),"JuanManuel","Prividera"
        );
        colaboracionPuntuablePrueba = colaboracionBuilder.
                crearColaboracion(TipoColaboracionPuntuable.DINERO, LocalDate.of(2024,5,21),colaboradorPrueba,2);
    }

    @Test @DisplayName("Se leyeron la cantidad de filas esperadas")
    public void leerColaboradoresTestCantidadDeFilas() throws IOException, CsvException {
        colaboraciones = lectorCSV.leerColaboradores();
        Assertions.assertEquals(4, colaboraciones.size());
    }

    @Test @DisplayName("Se crea correctamente la clase colaboracion")
    public void creacionDeColaboracionTest() throws IOException, CsvException {
        colaboraciones = lectorCSV.leerColaboradores();
        ColaboracionPuntuable colaboracionPuntuable = colaboraciones.get(0);
        Assertions.assertEquals(colaboracionPuntuable.getColaborador().getDocumento().getNroDocumento(), colaboracionPuntuablePrueba.getColaborador().getDocumento().getNroDocumento());
        Assertions.assertEquals(colaboracionPuntuable.getColaborador().getApellido(), colaboracionPuntuablePrueba.getColaborador().getApellido());
    }

    @Test @DisplayName("Se crean bien las colaboraciones")
    public void seMuestranLasColaboracionesLeidas() throws IOException, CsvException {
        colaboraciones = lectorCSV.leerColaboradores();
        for (ColaboracionPuntuable colaboracionPuntuable : colaboraciones) {
            System.out.println(colaboracionPuntuable.getColaborador().getNombre());
        }
    }


}
