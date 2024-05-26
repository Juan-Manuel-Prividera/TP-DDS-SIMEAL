package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.AdministradorAlertas;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Medicion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.TipoMedicion;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AlertasTest {
  Heladera heladera;
  AdministradorAlertas administradorAlertas;
  Medicion medicionMovimiento;
  Medicion medicionCritica;


  @BeforeEach
  public void init() {
    heladera = new Heladera(new Ubicacion("Calle",123), LocalDate.now(),"Heladera",null);
    administradorAlertas = new AdministradorAlertas(heladera);
    medicionMovimiento = new Medicion(LocalDate.now(),"Temeratura normal",true, TipoMedicion.MOVIMIENTO);
    medicionCritica = new Medicion(LocalDate.now(),"Se nos derrite la heladera",true, TipoMedicion.TEMPERATURA);
  }

  @Test @DisplayName("Si le llega una medicion critica al Administrador cambia el estado de la heladera y genera el desperfecto")
  public void respoderAlertaMedicionCriticaTemperatura(){
    administradorAlertas.responderAlerta(medicionCritica);
    Assertions.assertFalse(heladera.validarEstado());
    Assertions.assertEquals(1,heladera.getDesperfectos().size());
  }

  @Test @DisplayName("Si le llega una medicion de movimiento al Administrador cambia el estado de la heladera y genera el desperfecto")
  public void respoderAlertaMedicionCriticaMovimiento(){
    administradorAlertas.responderAlerta(medicionMovimiento);
    Assertions.assertFalse(heladera.validarEstado());
    Assertions.assertEquals(1,heladera.getDesperfectos().size());
  }

}
