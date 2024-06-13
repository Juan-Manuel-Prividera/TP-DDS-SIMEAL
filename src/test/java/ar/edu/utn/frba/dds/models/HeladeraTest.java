package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Modelo;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.Activa;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.EnReparacion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.Inactiva;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.FallaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Incidente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Medicion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.TipoAlerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.TipoMedicion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HeladeraTest {
  Ubicacion ubicacion= new Ubicacion(555,444);
  Modelo modelo= new Modelo(20,-10,50);
  Heladera heladera = new Heladera(ubicacion, LocalDate.now(), "heladera feliz", modelo);
  Activa estadoActivo = new Activa();
  Inactiva estadoInactivo = new Inactiva();
  EnReparacion estadoEnReparacion = new EnReparacion();
  Medicion medicion = new Medicion(TipoMedicion.TEMPERATURA);
  Colaborador colaborador = new Colaborador(
      new Documento(TipoDocumento.DNI, "1234567"),
      "Karl", "Heun");
  Incidente alerta = new Alerta(heladera,
      "Esto es una alerta de movimiento.",
      LocalDateTime.now(),
      TipoAlerta.ALERTA_FRAUDE);

  Incidente fallaTecnica = new FallaTecnica(heladera,
      "Esto describe el problema que encontró el colaborador",
      LocalDateTime.now(),
      colaborador, null);

  @Test @DisplayName("Cambiar estado a Activa")
  public void testEstado(){
    heladera.cambiarDeEstado(estadoActivo);
    Assertions.assertEquals(heladera.getEstado(), estadoActivo);
  }

  @Test @DisplayName("Estado heladera == Activa => Validar estado == True")
  public void testValidarEstadoActivo(){
    heladera.cambiarDeEstado(estadoActivo);
    Assertions.assertTrue(heladera.estaDisponible());
  }

  @Test @DisplayName("Estado heladera == Inactivo => Validar estado == False")
  public void testValidarEstadoInactivo(){
    heladera.cambiarDeEstado(estadoInactivo);
    Assertions.assertFalse(heladera.estaDisponible());
  }
  @Test @DisplayName("Estado heladera == En reparación => Validar estado == False")
  public void testValidarEstadoEnReparacion(){
    heladera.cambiarDeEstado(estadoEnReparacion);
    Assertions.assertFalse(heladera.estaDisponible());
  }

  // No es realmente un test, perdón :ashamed:
  @Test @DisplayName("Se reporta un incidente => se loggea (alerta)")
  public void testRegistrarIncidenteAlerta() {
    System.out.println("====== Test reportar una alerta =====");
    heladera.reportarIncidente(alerta);
    System.out.println("=====================================");
  }

  // Ídem línea 68
  @Test @DisplayName("Se reporta un incidente => se loggea (falla técnica)")
  public void testRegistrarIncidenteFallaTecnica() {
    System.out.println("== Test reportar una falla tecnica ==");
    heladera.reportarIncidente(fallaTecnica);
    System.out.println("=====================================");
  }

}
