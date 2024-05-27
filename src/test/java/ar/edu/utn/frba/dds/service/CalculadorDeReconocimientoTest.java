package ar.edu.utn.frba.dds.service;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.service.CalculadorDeReconocimientos;
import ar.edu.utn.frba.dds.simeal.service.ColaboracionBuilder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculadorDeReconocimientoTest {
    CalculadorDeReconocimientos calculadorDeReconocimientos;
    Colaborador colaborador;
    List<Colaboracion> colaboraciones;
    ColaboracionBuilder colaboracionBuilder;

    @BeforeEach
    public void init(){
        colaboraciones = new ArrayList<>();
        colaborador = new Colaborador(new Documento(TipoDocumento.DNI,"12345678"),"Juan","Sanchez"
        );
        colaboracionBuilder = new ColaboracionBuilder();
        colaboraciones.add(colaboracionBuilder.crearColaboracion(TipoColaboracion.DINERO, LocalDate.now(),colaborador,10)); // 10 * 0.5 = 5
        colaboraciones.add(colaboracionBuilder.crearColaboracion(TipoColaboracion.DONACION_VIANDA,LocalDate.now(),colaborador,1)); // 1*1.5 = 1.5
        colaboraciones.add(colaboracionBuilder.crearColaboracion(TipoColaboracion.ENTREGA_TARJETA,LocalDate.now(),colaborador,1)); // 1 * 2 = 2
        colaboraciones.add(colaboracionBuilder.crearColaboracion(TipoColaboracion.REDISTRIBUCION_VIANDA,LocalDate.now(),colaborador,4)); // 4 * 1 = 4
    }

    @Test
    public void calculoDeReconocimientoSinHeladera(){
        calculadorDeReconocimientos = new CalculadorDeReconocimientos(colaboraciones);                                                 // Total = 12.5
        double reconocimiento = calculadorDeReconocimientos.calcularReconocimientoTotal(colaborador);
        Assertions.assertEquals(12.5, reconocimiento);
    }

    @Test
    public void calculoDeReconocimientoConHeladera() {
        colaboraciones.add(colaboracionBuilder.crearColaboracion(TipoColaboracion.ADHERIR_HELADERA,LocalDate.of(2023,5,23),colaborador,10));
        calculadorDeReconocimientos = new CalculadorDeReconocimientos(colaboraciones);                                                 // Total = 12.5
        double reconocimiento = calculadorDeReconocimientos.calcularReconocimientoTotal(colaborador);
        // (12 meses * 5 ) + 12.5 = 60 + 12.5 = 72.5
        Assertions.assertEquals(72.5,reconocimiento);
    }

}
