package ar.edu.utn.frba.dds.models;


import ar.edu.utn.frba.dds.simeal.models.creacionales.ColaboracionBuilder;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
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
    ColaboracionPuntuable distribuirVianda = ColaboracionBuilder
        .crearColaboracion(TipoColaboracion.REDISTRIBUCION_VIANDA,LocalDate.now(),colaborador,2);
    Assertions.assertEquals(2, colaborador.getPuntosDeReconocimientoParcial());
  }

  @Test
  public void createDonacionDinero() {
    ColaboracionPuntuable donacionDinero = ColaboracionBuilder
        .crearColaboracion(TipoColaboracion.DINERO, LocalDate.now(),colaborador,2);
    Assertions.assertEquals(1, colaborador.getPuntosDeReconocimientoParcial());
  }

  @Test
  public void createDonacionVianda() {
    ColaboracionPuntuable donacionVianda = ColaboracionBuilder
        .crearColaboracion(TipoColaboracion.DONACION_VIANDA,LocalDate.now(),colaborador,2);
    Assertions.assertEquals(1.5, colaborador.getPuntosDeReconocimientoParcial());
  }

  @Test
  public void createDarDeAltaPersonaVulnerable() {
    ColaboracionPuntuable darDeAltaPersona = ColaboracionBuilder
        .crearColaboracion(TipoColaboracion.ENTREGA_TARJETA, LocalDate.now(),colaborador,2);
    Assertions.assertEquals(2, colaborador.getPuntosDeReconocimientoParcial());
  }
}
