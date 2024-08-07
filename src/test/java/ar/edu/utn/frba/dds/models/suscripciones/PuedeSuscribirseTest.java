package ar.edu.utn.frba.dds.models.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.HayMuchasViandas;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PuedeSuscribirseTest {
  Ubicacion ubicacionCercana;
  Ubicacion ubicacionLegaja;
  Ubicacion ubicacionHeladera;
  Heladera heladera;
  Suscripcion suscripcion;
  @BeforeEach
  public void init() {
    ubicacionHeladera = new Ubicacion(-34.59863378269025, -58.420124757795726);
    heladera = new Heladera(ubicacionHeladera);
    ubicacionCercana = new Ubicacion(-34.595248855276196, -58.41984904689641);
    ubicacionLegaja = new Ubicacion(-34.56812363242786, -58.42614710949405);
    suscripcion = new Suscripcion(heladera, null,1,null);
  }



  @Test @DisplayName("Si esta lo suficientemente cerca => Se puede suscribir")
  public void siEstaSuficientementeCerca() {
    Colaborador suscriptor = new Colaborador(ubicacionCercana);
    Assertions.assertTrue(suscripcion.puedeSuscribirse(suscriptor));
  }

  @Test @DisplayName("Si no esta lo suficientemente cerca => No se puede suscribir")
  public void siEstaLejosNoPuedeSuscribirse() {
    Colaborador suscriptor = new Colaborador(ubicacionLegaja);
    Assertions.assertFalse(suscripcion.puedeSuscribirse(suscriptor));

  }

}
