package ar.edu.utn.frba.dds.service;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.service.CalculadorDeReconocimientos;
import ar.edu.utn.frba.dds.simeal.service.ColaboracionBuilder;
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
        ColaboracionPuntuable colaboracion1 = ColaboracionBuilder.crearColaboracionPuntuable(TipoColaboracion.DINERO, LocalDate.now(),colaborador,10); // 10 * 0.5 = 5
        ColaboracionPuntuable colaboracion2 = ColaboracionBuilder.crearColaboracionPuntuable(TipoColaboracion.DONACION_VIANDA,LocalDate.now(),colaborador,1); // 1*1.5 = 1.5
        ColaboracionPuntuable colaboracion3 = ColaboracionBuilder.crearColaboracionPuntuable(TipoColaboracion.ENTREGA_TARJETA,LocalDate.now(),colaborador,1); // 1 * 2 = 2
        ColaboracionPuntuable colaboracion4 = ColaboracionBuilder.crearColaboracionPuntuable(TipoColaboracion.REDISTRIBUCION_VIANDA,LocalDate.now(),colaborador,4); // 4 * 1 = 4
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
            .crearColaboracionPuntuable(TipoColaboracion.ADHERIR_HELADERA,LocalDate.of(2023,5,23),colaborador,10));

        double reconocimiento = CalculadorDeReconocimientos.calcularReconocimientoTotal(colaborador,adherirHeladera);
        // (12 meses * 5 ) + 12.5 = 60 + 12.5 = 72.5
        Assertions.assertEquals(72.5,reconocimiento);
    }
}
