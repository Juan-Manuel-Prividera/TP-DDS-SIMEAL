package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UbicacionTest {
  Ubicacion bsas;
  Ubicacion laPlata;

  @BeforeEach
  public void init() {
    bsas = new Ubicacion(-58.3816,-34.6037);
    laPlata = new Ubicacion(-57.9536, -34.9205);
  }

  @Test
  public void distanciaCorrectaEntreBsAsYLaPlata() {
    double expected = 51187.50627793464;
    Assertions.assertEquals(expected, bsas.distanciaA(laPlata));
  }

  @Test
  public void distanciaConSiMismoEs0() {
    Assertions.assertEquals(0, bsas.distanciaA(bsas));
  }

  @Test
  public void esConmutativa() {
    Assertions.assertEquals(bsas.distanciaA(laPlata), laPlata.distanciaA(bsas));
  }

}
