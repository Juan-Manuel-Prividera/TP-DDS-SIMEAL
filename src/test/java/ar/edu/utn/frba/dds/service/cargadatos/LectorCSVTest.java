package ar.edu.utn.frba.dds.service.cargadatos;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.donarDinero.DonarDinero;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.service.ColaboracionBuilder;
import ar.edu.utn.frba.dds.simeal.service.cargadorDatos.LectorCSV;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LectorCSVTest {
    String csvFile = "src/main/java/ar/edu/utn/frba/dds/simeal/service/cargadorDatos/datos.csv";
    LectorCSV lectorCSV;
    List<Colaboracion> colaboraciones;
    Colaboracion colaboracionPrueba;
    ColaboracionBuilder colaboracionBuilder;
    Colaborador colaboradorPrueba;


    @BeforeEach
    public void init() throws FileNotFoundException {
        lectorCSV = new LectorCSV(csvFile);
        colaboraciones = new ArrayList<>();
        colaboracionBuilder = new ColaboracionBuilder();

        colaboradorPrueba = new Colaborador(new Documento(TipoDocumento.DNI,"01234567"),"JuanManuel","Prividera");
        colaboracionPrueba = colaboracionBuilder.
                crearColaboracion(TipoColaboracion.DINERO, LocalDate.of(2024,5,21),colaboradorPrueba,2);
    }

    @Test @DisplayName("Se leyeron la cantidad de filas esperadas")
    public void leerColaboradoresTestCantidadDeFilas() throws IOException, CsvException {
        colaboraciones = lectorCSV.leerColaboradores();
        Assertions.assertEquals(4, colaboraciones.size());
    }

    @Test @DisplayName("Se crea correctamente la clase colaboracion")
    public void creacionDeColaboracionTest() throws IOException, CsvException {
        colaboraciones = lectorCSV.leerColaboradores();
        Colaboracion colaboracion = colaboraciones.get(0);
        Assertions.assertEquals(colaboracion.getColaborador().getDocumento().getNroDocumento(),colaboracionPrueba.getColaborador().getDocumento().getNroDocumento());
        Assertions.assertEquals(colaboracion.getColaborador().getApellido(), colaboracionPrueba.getColaborador().getApellido());
    }




}
