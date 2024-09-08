package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.ModeloHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.VisitaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.TipoAlerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Medicion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.MedicionTemperatura;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Notificador;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.ReceptorDeNotificaciones;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class HeladeraTest {
  Ubicacion ubicacion;
  ModeloHeladera modelo;
  Heladera heladera;

  Medicion medicion;
  Colaborador colaborador;
  Incidente alerta;

  Incidente fallaTecnica;

  VisitaTecnica visitaExitosa;

  MockedStatic<Notificador> notificadorMock;

  @BeforeEach
  public void init() {
    ubicacion= new Ubicacion(555,444);
    modelo= new ModeloHeladera("hola",20,-10,50);
    heladera = new Heladera(ubicacion, LocalDate.now(), "heladera feliz", modelo);
    heladera.setHeladerasCercanas(List.of(heladera));
    medicion = new MedicionTemperatura();
    colaborador = new Colaborador(
      new Documento(TipoDocumento.DNI, "1234567"),
      "Karl", "Heun");
    alerta = new Alerta(heladera,
      "Esto es una alerta de movimiento.",
      TipoAlerta.ALERTA_FRAUDE);

    fallaTecnica = new FallaTecnica(heladera,
      "Esto describe el problema que encontró el colaborador",
      LocalDateTime.now(),
      colaborador, null);

    visitaExitosa = new VisitaTecnica(heladera,
      "Arreglado del condensador de flujo de la heladera.",
      LocalDateTime.now(),
      true, null);

    notificadorMock = mockStatic(Notificador.class);
    notificadorMock.when(() -> Notificador.notificar((List<? extends ReceptorDeNotificaciones>) any(),any())).thenAnswer(invocationOnMock -> null);

  }
  @AfterEach
  public void after() {
    notificadorMock.close();
  }

  @Test @DisplayName("Cambiar estado a Activa")
  public void testEstado(){
    heladera.activar();
    Assertions.assertTrue(heladera.getActiva());
  }

  @Test
  public void temperaturaMuyFria() {
    double tempFria = -15;
    Assertions.assertFalse(heladera.temperaturaAdecuada(tempFria));
  }

  @Test
  public void temperaturaMuyCaliente() {
    double tempCaliente = 50;
    Assertions.assertFalse(heladera.temperaturaAdecuada(tempCaliente));
  }

  @Test
  public void temperaturaAdecuada() {
    double tempAdecuada = 10;
    Assertions.assertTrue(heladera.temperaturaAdecuada(tempAdecuada));
  }

  @Test @DisplayName("Estado heladera == Activa => Heladera está disponible")
  public void testValidarEstadoActivo(){
    heladera.activar();
    Assertions.assertTrue(heladera.estaDisponible());
  }

  @Test @DisplayName("Estado heladera == Inactivo o en Reparacion => Heladera no está disponible")
  public void testValidarEstadoInactivo(){
    heladera.desactivar();
    Assertions.assertFalse(heladera.estaDisponible());
  }

  @Test @DisplayName("Se reporta un incidente => la heladera no está disponible")
  public void testReportarIncidenteHeladeraNoDisponible() {
    heladera.reportarIncidente(alerta);
    Assertions.assertFalse(heladera.getActiva());
  }

//  // No es realmente un test, perdón :ashamed:
//  @Test @DisplayName("Se reporta un incidente => se loggea (alerta)")
//  public void testRegistrarIncidenteAlerta() {
//    System.out.println("====== Test reportar una alerta =====");
//    heladera.reportarIncidente(alerta);
//    System.out.println("=====================================");
//  }
//
//  // Ídem línea 68
//  @Test @DisplayName("Se reporta un incidente => se loggea (falla técnica)")
//  public void testRegistrarIncidenteFallaTecnica() {
//    System.out.println("== Test reportar una falla tecnica ==");
//    heladera.reportarIncidente(fallaTecnica);
//    System.out.println("=====================================");
//  }
}
