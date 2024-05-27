package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Desperfecto;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Modelo;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.Activa;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.Inactiva;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Medicion;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HeladeraTest {
  Ubicacion ubicacion;
  Modelo modelo;
  Heladera heladera;
  Activa estadoActivo;
  Inactiva estadoInactivo;
  Medicion medicion;
  Desperfecto desperfecto;

  @BeforeEach
  public void init(){
    ubicacion = new Ubicacion(555,444);
    modelo = new Modelo();
    heladera = new Heladera(ubicacion, LocalDate.now(), "heladera feliz", modelo);
    estadoActivo = new Activa();
    estadoInactivo = new Inactiva();
    medicion = new Medicion();
    desperfecto = new Desperfecto("Nada puede ser perfecto", medicion);

  }


  @Test @DisplayName("Cambiar estado a Activa")
  public void testEstado(){
    heladera.cambiarDeEstado(estadoActivo);
    Assertions.assertEquals(heladera.getEstado(), estadoActivo);
  }

  @Test @DisplayName("Estado heladera == Activa => Validar estado == True")
  public void testValidarEstadoActivo(){
    heladera.cambiarDeEstado(estadoActivo);
    Assertions.assertTrue(heladera.validarEstado());
  }


  @Test @DisplayName("Estado heladera == Inactivo => Validar estado == False")
  public void testValidarEstadoInactivo(){
    heladera.cambiarDeEstado(estadoInactivo);
    Assertions.assertFalse(heladera.validarEstado());
  }

  @Test @DisplayName("Heladera activa => Notificación == 'La heladera se encuentra activa:' + 'mensaje default'")
  public void testNotificacion(){
    heladera.cambiarDeEstado(estadoActivo);
    Assertions.assertEquals(heladera.enviarNotificacionPorDefecto("mensaje default"), "La heladera se encuentra activa: mensaje default");
  }

  @Test @DisplayName("Agregar desperfecto a heladera")
  public void testAgregarDesperfecto(){
    heladera.agregarDesperfecto(desperfecto);
    Assertions.assertTrue(heladera.getDesperfectos().contains(desperfecto));
  }
}
