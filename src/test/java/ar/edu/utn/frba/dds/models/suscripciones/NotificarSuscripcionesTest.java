package ar.edu.utn.frba.dds.models.suscripciones;

import ar.edu.utn.frba.dds.simeal.controllers.AdministradorDeEventos;
import ar.edu.utn.frba.dds.simeal.models.creacionales.EventoFactory;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.TipoAlerta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.SugerenciaHeladeras;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.HayMuchasViandas;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.HuboUnDesperfecto;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.Notificacion;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.QuedanPocasViandas;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.SuscripcionesRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.TipoRepo;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import ar.edu.utn.frba.dds.simeal.service.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Notificador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.mockito.Mockito.*;

public class NotificarSuscripcionesTest {
  Heladera heladera;
  Vianda vianda1;
  Vianda vianda2;
  Vianda vianda3;
  Vianda vianda4;
  Vianda vianda5;

  Colaborador suscriptor;

  @BeforeEach
  public void setUp() {
    heladera = new Heladera(new Ubicacion("hola", 123));
    vianda1 = new Vianda(heladera);
    vianda2 = new Vianda(heladera);
    vianda3 = new Vianda(heladera);
    vianda4 = new Vianda(heladera);
    vianda5 = new Vianda(heladera);
    suscriptor = new Colaborador();

  }

  @AfterEach
  public void after(){

  }

  @Test @DisplayName("Quedan 4 viandas en la heladera => Se notifica a las suscripciones con cantidad minima 5")
  public void seEnviaLaNotificacionPorPocasViandas() {
    Notificacion notificacion = new QuedanPocasViandas(heladera);
    Suscripcion suscripcion = new Suscripcion(heladera,suscriptor,5, notificacion);

    // Mocks
    MockedStatic<Notificador> notificadorMock = mockStatic(Notificador.class);
    MockedStatic<EventoFactory> eventoFactoryMock = mockStatic(EventoFactory.class);
    ViandaRepository viandaRepository = mock(ViandaRepository.class);
    SuscripcionesRepository suscripcionesRepository = mock(SuscripcionesRepository.class);

    // Defino comportamiento de Mocks
    doReturn(List.of(vianda1,vianda2,vianda3,vianda4)).when(viandaRepository).buscarPorHeladera(heladera);
    doReturn(List.of(suscripcion)).when(suscripcionesRepository).buscarPor(heladera);
    notificadorMock.when(() -> Notificador.notificar(suscriptor,notificacion.getMensaje())).thenAnswer(invocationOnMock -> null);
    eventoFactoryMock.when(() -> EventoFactory.crearEvento(heladera, TipoEvento.RETIRO)).thenCallRealMethod();

    AdministradorDeEventos administradorDeEventos = new AdministradorDeEventos(viandaRepository, suscripcionesRepository);
    eventoFactoryMock.when(EventoFactory::crearAdministrador).thenReturn(administradorDeEventos);

    // Disparo el evento
    vianda5.retirar();

    // Validacion del manejo del evento
    notificadorMock.verify(() -> Notificador.notificar(List.of(suscriptor),notificacion.getMensaje()));
    notificadorMock.close();
    eventoFactoryMock.close();

  }


  @Test @DisplayName("Cuando se hace un setHeladera(heladera) (retiro e ingreso) se disparan dos eventos => Se notifica a dos suscripciones")
  public void seEnviaNotificacionPorMuchasViandas() {
    Notificacion notificacion = new QuedanPocasViandas(heladera);
    Suscripcion suscripcion = new Suscripcion(heladera,suscriptor,5, notificacion);
    Heladera heladera2 = new Heladera();
    Vianda vianda6 = new Vianda(heladera2);
    Vianda vianda7 = new Vianda(heladera2);
    Notificacion notificacion2 = new HayMuchasViandas(heladera2);
    Suscripcion suscripcion1 = new Suscripcion(heladera2, suscriptor,2, notificacion2);

    // Mocks
    MockedStatic<Notificador> notificadorMock = mockStatic(Notificador.class);
    MockedStatic<EventoFactory> eventoFactoryMock = mockStatic(EventoFactory.class);
    ViandaRepository viandaRepository = mock(ViandaRepository.class);
    SuscripcionesRepository suscripcionesRepository = mock(SuscripcionesRepository.class);

    // Defino comportamiento de Mocks
    doReturn(List.of(vianda1,vianda2,vianda3,vianda4)).when(viandaRepository).buscarPorHeladera(heladera);
    doReturn(List.of(vianda6,vianda7,vianda5)).when(viandaRepository).buscarPorHeladera(heladera2);
    doReturn(List.of(suscripcion)).when(suscripcionesRepository).buscarPor(heladera);
    doReturn(List.of(suscripcion1)).when(suscripcionesRepository).buscarPor(heladera2);
    notificadorMock.when(() -> Notificador.notificar(suscriptor,notificacion.getMensaje())).thenAnswer(invocationOnMock -> null);
    notificadorMock.when(() -> Notificador.notificar(suscriptor,notificacion2.getMensaje())).thenAnswer(invocationOnMock -> null);
    eventoFactoryMock.when(() -> EventoFactory.crearEvento(heladera, TipoEvento.RETIRO)).thenCallRealMethod();
    eventoFactoryMock.when(() -> EventoFactory.crearEvento(heladera2, TipoEvento.INGRESO)).thenCallRealMethod();

    AdministradorDeEventos administradorDeEventos = new AdministradorDeEventos(viandaRepository, suscripcionesRepository);
    eventoFactoryMock.when(EventoFactory::crearAdministrador).thenReturn(administradorDeEventos);

    vianda5.setHeladera(heladera2);

    // Validacion del manejo del evento
    notificadorMock.verify(() -> Notificador.notificar(List.of(suscriptor),notificacion.getMensaje()));
    notificadorMock.verify(() -> Notificador.notificar(List.of(suscriptor),notificacion2.getMensaje()));

    notificadorMock.close();
    eventoFactoryMock.close();
  }

//  @Test @DisplayName("Cuando se reporta incidente se notifica a todos los suscriptores de incidentes")
//  public void seEnviaNotificacionPorDesperfecto() {
//    Alerta alerta = new Alerta(heladera,"descripcion de alerta", TipoAlerta.ALERTA_FRAUDE);
//
//    SugerenciaHeladeras sugerenciaHeladeras = mock (SugerenciaHeladeras.class);
//    doReturn("").when(sugerenciaHeladeras).getSugerencia(2);
//
//    Notificacion notificacion = new HuboUnDesperfecto(heladera,sugerenciaHeladeras);
//    Suscripcion suscripcion = new Suscripcion(heladera,suscriptor,5, notificacion);
//
//    // Mocks
//    MockedStatic<Notificador> notificadorMock = mockStatic(Notificador.class);
//    MockedStatic<EventoFactory> eventoFactoryMock = mockStatic(EventoFactory.class);
//    MockedStatic<ServiceLocator> serviceLocatorMocke = mockStatic(ServiceLocator.class);
//    ViandaRepository viandaRepository = mock(ViandaRepository.class);
//    SuscripcionesRepository suscripcionesRepository = mock(SuscripcionesRepository.class);
//    Repositorio heladeraRepository = mock(Repositorio.class);
//
//    // Defino comportamiento de Mocks
//    doReturn(List.of(vianda1,vianda2,vianda3,vianda4)).when(viandaRepository).buscarPorHeladera(heladera);
//    doReturn(List.of(suscripcion)).when(suscripcionesRepository).buscarPor(heladera);
//    notificadorMock.when(() -> Notificador.notificar(suscriptor,notificacion.getMensaje())).thenAnswer(invocationOnMock -> null);
//    eventoFactoryMock.when(() -> EventoFactory.crearEvento(heladera, TipoEvento.INCIDENTE)).thenCallRealMethod();
//    serviceLocatorMocke.when(() -> ServiceLocator.getRepository(TipoRepo.HELADERA)).thenReturn(heladeraRepository);
//
//
//    AdministradorDeEventos administradorDeEventos = new AdministradorDeEventos(viandaRepository, suscripcionesRepository);
//    eventoFactoryMock.when(EventoFactory::crearAdministrador).thenReturn(administradorDeEventos);
//
//
//    heladera.reportarIncidente(alerta);
//
//    // Validacion del manejo del evento
//    notificadorMock.verify(() -> Notificador.notificar(List.of(suscriptor),notificacion.getMensaje()));
//
//    notificadorMock.close();
//    eventoFactoryMock.close();
//
//  }
}
