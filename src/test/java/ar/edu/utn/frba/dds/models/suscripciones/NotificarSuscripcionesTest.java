package ar.edu.utn.frba.dds.models.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.TipoAlerta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.*;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.AdministradorDeEventos;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.SuscripcionesRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import ar.edu.utn.frba.dds.simeal.service.Notificador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class NotificarSuscripcionesTest {
  MockedStatic<Notificador> notificadorMock;
  AdministradorDeEventos administradorDeEventos;

  SuscripcionesRepository suscripcionesRepositoryMock;
  ViandaRepository viandaRepositoryMock;

  Heladera heladera;
  Colaborador suscriptor;
  Colaborador suscriptor1;
  List<Vianda> viandas = new ArrayList<>();
  Vianda vianda1 = new Vianda();
  Vianda vianda2 = new Vianda();
  Vianda vianda3 = new Vianda();
  Vianda vianda4 = new Vianda();

  Vianda vianda;

  @BeforeEach
  public void setUp() {
    notificadorMock = mockStatic(Notificador.class);
    administradorDeEventos = new AdministradorDeEventos();

    suscripcionesRepositoryMock = mock(SuscripcionesRepository.class);
    viandaRepositoryMock = mock(ViandaRepository.class);
    administradorDeEventos.setSuscripcionesRepository(suscripcionesRepositoryMock);
    administradorDeEventos.setViandaRepository(viandaRepositoryMock);

    heladera = new Heladera(new Ubicacion(2,3), administradorDeEventos);
    viandas.add(vianda1);
    viandas.add(vianda2);
    viandas.add(vianda3);
    viandas.add(vianda4);
    vianda = new Vianda(heladera,administradorDeEventos);
  }

  @AfterEach
  public void after(){
    notificadorMock.close();
  }

  @Test @DisplayName("Quedan 4 viandas en la heladera => Se notifica a las suscripciones con cantidad minima 4")
  public void seEnviaLaNotificacionPorPocasViandas() {
    suscriptor = new Colaborador();
    List<Colaborador> suscriptores = new ArrayList<>();
    suscriptores.add(suscriptor);

    Suscripcion suscripcion = new Suscripcion(heladera, suscriptor, 4,15);

    List<Suscripcion> suscripciones = new ArrayList<>();
    suscripciones.add(suscripcion);

    when(viandaRepositoryMock.buscarPorHeladera(heladera)).thenReturn(viandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera,TipoEvento.RETIRO)).thenReturn(suscripciones);

    notificadorMock.when(() -> Notificador.notificar(eq(suscriptores),any())).thenAnswer(invocationOnMock -> null);

    vianda.retirar();

    notificadorMock.verify(() -> Notificador.notificar(eq(suscriptores),any()),times(1));
  }

  @Test @DisplayName("Cuando un moverA (retiro e ingreso) se disparan dos eventos => Se notifica a dos suscripciones")
  public void seEnviaNotificacionPorMuchasViandas() {
    Vianda vianda = new Vianda(heladera,administradorDeEventos);

    suscriptor = new Colaborador();
    List<Colaborador> suscriptores = new ArrayList<>();
    suscriptores.add(suscriptor);
    Suscripcion suscripcion = new Suscripcion(heladera, suscriptor, 4,15);
    List<Suscripcion> suscripciones = new ArrayList<>();
    suscripciones.add(suscripcion);

    suscriptor1 = new Colaborador();
    Suscripcion suscripcion1 = new Suscripcion(heladera, suscriptor1, 0, 2);
    List<Colaborador> suscriptores1 = new ArrayList<>();
    suscriptores1.add(suscriptor1);
    List<Suscripcion> suscripciones1 = new ArrayList<>();
    suscripciones1.add(suscripcion1);

    when(viandaRepositoryMock.buscarPorHeladera(heladera)).thenReturn(viandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera,TipoEvento.RETIRO)).thenReturn(suscripciones);
    when(suscripcionesRepositoryMock.buscarPor(heladera,TipoEvento.INGRESO)).thenReturn(suscripciones1);

    notificadorMock.when(() -> Notificador.notificar(eq(suscriptores),any())).thenAnswer(invocationOnMock -> null);
    vianda.retirar();
    notificadorMock.verify(() -> Notificador.notificar(eq(suscriptores),any()),times(1));


    notificadorMock.when(() -> Notificador.notificar(eq(suscriptores1),any())).thenAnswer(invocationOnMock -> null);
    vianda.ingresarA(heladera);
    notificadorMock.verify(() -> Notificador.notificar(eq(suscriptores1),any()),times(1));

  }

  @Test @DisplayName("Cuando se reporta incidente se notifica a todos los suscriptores de incidentes")
  public void seEnviaNotificacionPorDesperfecto() {
    Alerta alerta = new Alerta(heladera,"descripcion de alerta", TipoAlerta.ALERTA_FRAUDE);
    Vianda vianda = new Vianda(heladera,administradorDeEventos);
    suscriptor = new Colaborador();
    List<Colaborador> suscriptores = new ArrayList<>();
    suscriptores.add(suscriptor);

    Suscripcion suscripcion = new Suscripcion(heladera, suscriptor, 4,15);
    List<Suscripcion> suscripciones = new ArrayList<>();
    suscripciones.add(suscripcion);

    when(viandaRepositoryMock.buscarPorHeladera(heladera)).thenReturn(viandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera,TipoEvento.INCIDENTE)).thenReturn(suscripciones);
    notificadorMock.when(() -> Notificador.notificar(eq(suscriptores),any())).thenAnswer(invocationOnMock -> null);

    administradorDeEventos.setSuscripcionesRepository(suscripcionesRepositoryMock);
    administradorDeEventos.setViandaRepository(viandaRepositoryMock);

    heladera.reportarIncidente(new Alerta(heladera,"descripcion de alerta", TipoAlerta.ALERTA_FRAUDE));

    notificadorMock.verify(() -> Notificador.notificar(eq(suscriptores),any()));

  }
}
