package ar.edu.utn.frba.dds.utils.cargadatos;

import ar.edu.utn.frba.dds.simeal.models.creacionales.ColaboracionBuilder;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.utils.cargadordatos.LectorCsv;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Notificador;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.ReceptorDeNotificaciones;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class LectorCSVTest {
    String csvFile = "src/main/java/ar/edu/utn/frba/dds/simeal/utils/cargadordatos/datos.csv";
    LectorCsv lectorCSV;
    List<ColaboracionPuntuable> colaboraciones;
    ColaboracionPuntuable colaboracionPrueba;
    ColaboracionBuilder colaboracionBuilder;
    Colaborador colaboradorPrueba;
    MockedStatic<Notificador> notificadorMock;

    @AfterEach
    public void after() {
        notificadorMock.close();
    }
    @BeforeEach
    public void init() throws FileNotFoundException {
        lectorCSV = new LectorCsv();
        colaboraciones = new ArrayList<>();
        colaboracionBuilder = new ColaboracionBuilder();
        notificadorMock = mockStatic(Notificador.class);
        notificadorMock.when(() -> Notificador.notificar((List<? extends ReceptorDeNotificaciones>) any(),any())).thenAnswer(invocationOnMock -> null);
        colaboradorPrueba = new Colaborador(
            new Documento(TipoDocumento.DNI,"01234567"),"JuanManuel","Prividera");
        colaboracionPrueba = ColaboracionBuilder.
          crearColaboracion(TipoColaboracion.DINERO, LocalDate.of(2024,5,21),colaboradorPrueba,2);
    }

    @Test @DisplayName("Se leyeron la cantidad de filas esperadas")
    public void leerColaboradoresTestCantidadDeFilas() throws IOException, CsvException {
        colaboraciones = lectorCSV.leerColaboradores(csvFile);
        Assertions.assertEquals(5, colaboraciones.size());
    }

    @Test @DisplayName("Se crea correctamente la clase colaboracion")
    public void creacionDeColaboracionTest() throws IOException, CsvException {
        colaboraciones = lectorCSV.leerColaboradores(csvFile);
        ColaboracionPuntuable colaboracion = colaboraciones.get(0);
        Assertions.assertEquals(colaboracion.getColaborador().getDocumento().getNroDocumento(), colaboracionPrueba.getColaborador().getDocumento().getNroDocumento());
        Assertions.assertEquals(colaboracion.getColaborador().getApellido(), colaboracionPrueba.getColaborador().getApellido());
    }

   @Test
   public void validacionDePuntosDeReconocimiento() throws IOException, CsvException {
        colaboraciones = lectorCSV.leerColaboradores(csvFile);
        Colaborador colaborador = colaboraciones.get(4).getColaborador();

        Assertions.assertEquals(7.5,colaborador.getPuntosDeReconocimientoParcial());
   }


}
