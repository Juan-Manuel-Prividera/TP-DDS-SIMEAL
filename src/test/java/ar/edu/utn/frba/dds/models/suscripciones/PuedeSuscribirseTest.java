package ar.edu.utn.frba.dds.models.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.HayMuchasViandas;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PuedeSuscribirseTest {
  Ubicacion ubicacionCercana;
  Ubicacion ubicacionLegaja;
  Ubicacion ubicacionHeladera;
  Heladera heladera;
  HayMuchasViandas suscripcion;
  @BeforeEach
  public void init() {
    ubicacionHeladera = new Ubicacion(-34.59863378269025, -58.420124757795726);
    heladera = new Heladera(ubicacionHeladera, null);
    ubicacionCercana = new Ubicacion(-34.595248855276196, -58.41984904689641);
    ubicacionLegaja = new Ubicacion(-34.56812363242786, -58.42614710949405);
    suscripcion = new HayMuchasViandas(heladera);
  }



  @Test
  public void siEstaSuficientementeCerca() {
    Colaborador suscriptor = new Colaborador(5, ubicacionCercana);
    suscripcion.suscribir(suscriptor);

    Assertions.assertTrue(suscripcion.getSuscriptores().contains(suscriptor));
  }

  @Test
  public void siEstaLejosNoPuedeSuscribirse() {
    Colaborador suscriptor = new Colaborador(4, ubicacionLegaja);
    suscripcion.suscribir(suscriptor);

    Assertions.assertFalse(suscripcion.getSuscriptores().contains(suscriptor));
  }

}
