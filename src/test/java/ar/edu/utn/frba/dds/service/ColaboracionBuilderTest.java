package ar.edu.utn.frba.dds.service;


import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.creacionales.ColaboracionBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ColaboracionBuilderTest {
  Colaborador colaborador;

  @BeforeEach
  public void init(){
    colaborador = new Colaborador(null, "Juan", "Perez");
  }

  @Test
  public void createDistribuirVianda() {
    Colaboracion distribuirVianda = ColaboracionBuilder
        .crearColaboracion(TipoColaboracion.REDISTRIBUCION_VIANDA,LocalDate.now(),colaborador,2);
    Assertions.assertEquals(2, colaborador.getPuntosDeReconocimientoParcial());
  }

  @Test
  public void createDonacionDinero() {
    Colaboracion donacionDinero = ColaboracionBuilder
        .crearColaboracion(TipoColaboracion.DINERO, LocalDate.now(),colaborador,2);
    Assertions.assertEquals(1, colaborador.getPuntosDeReconocimientoParcial());
  }

  @Test
  public void createDonacionVianda() {
    Colaboracion donacionVianda = ColaboracionBuilder
        .crearColaboracion(TipoColaboracion.DONACION_VIANDA,LocalDate.now(),colaborador,2);
    Assertions.assertEquals(1.5, colaborador.getPuntosDeReconocimientoParcial());
  }

  @Test
  public void createDarDeAltaPersonaVulnerable() {
    Colaboracion darDeAltaPersona = ColaboracionBuilder
        .crearColaboracion(TipoColaboracion.ENTREGA_TARJETA, LocalDate.now(),colaborador,2);
    Assertions.assertEquals(2, colaborador.getPuntosDeReconocimientoParcial());
  }
}
