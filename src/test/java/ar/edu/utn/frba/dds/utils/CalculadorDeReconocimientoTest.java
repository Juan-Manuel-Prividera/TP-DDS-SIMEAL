package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.simeal.models.creacionales.ColaboracionBuilder;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.utils.CalculadorDeReconocimientos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalculadorDeReconocimientoTest {
    Colaborador colaborador;

    @BeforeEach
    public void init(){
        colaborador = new Colaborador(
            new Documento(TipoDocumento.DNI,"12345678"),"Juan","Sanchez");
        ColaboracionPuntuable colaboracion1 = ColaboracionBuilder.crearColaboracion(TipoColaboracion.DINERO, LocalDate.now(),colaborador,10); // 10 * 0.5 = 5
        ColaboracionPuntuable colaboracion2 = ColaboracionBuilder.crearColaboracion(TipoColaboracion.DONACION_VIANDA,LocalDate.now(),colaborador,1); // 1*1.5 = 1.5
        ColaboracionPuntuable colaboracion3 = ColaboracionBuilder.crearColaboracion(TipoColaboracion.ENTREGA_TARJETA,LocalDate.now(),colaborador,1); // 1 * 2 = 2
        ColaboracionPuntuable colaboracion4 = ColaboracionBuilder.crearColaboracion(TipoColaboracion.REDISTRIBUCION_VIANDA,LocalDate.now(),colaborador,4); // 4 * 1 = 4
    }

    @Test
    public void calculoDeReconocimientoSinHeladera(){
        double reconocimiento = CalculadorDeReconocimientos.calcularReconocimientoTotal(colaborador, null);
        Assertions.assertEquals(12.5, reconocimiento);
    }

    @Test
    public void calculoDeReconocimientoConHeladera() {
        List<AdherirHeladera> adherirHeladera = new ArrayList<>();
        adherirHeladera.add((AdherirHeladera) ColaboracionBuilder
            .crearColaboracion(TipoColaboracion.ADHERIR_HELADERA,LocalDate.of(2023,6,23),colaborador,10));

        double reconocimiento = CalculadorDeReconocimientos.calcularReconocimientoTotal(colaborador,adherirHeladera);
        // (14 meses * 5 ) + 14.5 = 70 + 12.5 = 72.5
        Assertions.assertEquals(82.5,reconocimiento);
    }
}
