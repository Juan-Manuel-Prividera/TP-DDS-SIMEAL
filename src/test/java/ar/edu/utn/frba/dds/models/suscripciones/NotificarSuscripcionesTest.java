package ar.edu.utn.frba.dds.models.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.TipoAlerta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.ReceptorDeNotificaciones;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.*;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.AdministradorDeEventos;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.SuscripcionesRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import ar.edu.utn.frba.dds.simeal.service.Notificador;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class NotificarSuscripcionesTest {
  List<Suscripcion> suscripciones;
  Suscripcion quedanPocasViandas;
  Suscripcion hayMuchasViandas;
  Suscripcion huboDesperfecto;

  List<Vianda> viandas;
  Vianda vianda1;
  Vianda vianda2;
  Vianda vianda3;
  Vianda vianda4;
  Heladera heladera;

  List<Colaborador> suscriptores;
  Colaborador suscriptor1;
  Colaborador suscriptor2;
  Colaborador suscriptor3;

  List<ReceptorDeNotificaciones> interesadosEnPocasViandas;
  List<ReceptorDeNotificaciones> interesadosEnMuchasViandas;
  List<ReceptorDeNotificaciones> interesadosEnDesperfectos;

  SuscripcionesRepository suscripcionesRepositoryMock;
  ViandaRepository viandaRepositoryMock;
  MockedStatic<Notificador> notificadorMock;

  AdministradorDeEventos administradorDeEventos;

  @BeforeEach
  public void setUp() {
    administradorDeEventos = new AdministradorDeEventos();

    heladera = new Heladera(new Ubicacion(20,-30), administradorDeEventos);

    vianda1 = new Vianda(heladera,administradorDeEventos);
    vianda2 = new Vianda(heladera,administradorDeEventos);
    vianda3 = new Vianda(heladera,administradorDeEventos);
    vianda4 = new Vianda(heladera,administradorDeEventos);

    viandas = new ArrayList<>();
    viandas.add(vianda1);
    viandas.add(vianda2);
    viandas.add(vianda3);
    viandas.add(vianda4);

    interesadosEnPocasViandas = new ArrayList<>();
    interesadosEnMuchasViandas = new ArrayList<>();
    interesadosEnDesperfectos = new ArrayList<>();

    suscriptor1 = new Colaborador(1,null);
    suscriptor2 = new Colaborador(2, null);
    suscriptor3 = new Colaborador(4, null);

    suscriptores = new ArrayList<>();
    suscriptores.add(suscriptor1);
    suscriptores.add(suscriptor2);
    suscriptores.add(suscriptor3);

    quedanPocasViandas = new QuedanPocasViandas(heladera);
    quedanPocasViandas.setSuscriptores(suscriptores);
    hayMuchasViandas = new HayMuchasViandas(heladera);
    hayMuchasViandas.setSuscriptores(suscriptores);
    huboDesperfecto = new HuboUnDesperfecto(heladera);
    huboDesperfecto.setSuscriptores(suscriptores);

    suscripciones = new ArrayList<>();
    suscripciones.add(quedanPocasViandas);
    suscripciones.add(hayMuchasViandas);
    suscripciones.add(huboDesperfecto);

    notificadorMock = mockStatic(Notificador.class);
    viandaRepositoryMock = mock(ViandaRepository.class);
    suscripcionesRepositoryMock = mock(SuscripcionesRepository.class);


    interesadosEnPocasViandas.add(suscriptor1);
    interesadosEnPocasViandas.add(suscriptor2);

    interesadosEnMuchasViandas.add(suscriptor1);
    interesadosEnMuchasViandas.add(suscriptor2);


  }

  @AfterEach
  public void after(){
    notificadorMock.close();
  }

  @Test @DisplayName("Quedan 3 viandas en la heladera => Se notifica a los suscriptores con cantidad critica 3")
  public void seEnviaLaNotificacionPorPocasViandas() {
    viandas.remove(vianda1);
    when(viandaRepositoryMock.buscarPorHeladera(heladera)).thenReturn(viandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera, TipoEvento.RETIRO)).thenReturn(quedanPocasViandas);

    notificadorMock.when(() -> Notificador.notificar(interesadosEnPocasViandas,quedanPocasViandas.getMensaje())).thenAnswer(invocationOnMock -> null);

    administradorDeEventos.setSuscripcionesRepository(suscripcionesRepositoryMock);
    administradorDeEventos.setViandaRepository(viandaRepositoryMock);


    vianda1.retirar();

    notificadorMock.verify(() -> Notificador.notificar(interesadosEnPocasViandas,quedanPocasViandas.getMensaje()));
    notificadorMock.verify(() -> Notificador.notificar(interesadosEnMuchasViandas,hayMuchasViandas.getMensaje()), times(0));
    notificadorMock.verify(() -> Notificador.notificar(suscriptores,huboDesperfecto.getMensaje()),times(0));
  }

  @Test @DisplayName("Cuando se hace un moverA se ejecuta tambien el retirar")
  public void seEnviaNotificacionPorMuchasViandas() {
    viandas.remove(vianda1);
    when(viandaRepositoryMock.buscarPorHeladera(heladera)).thenReturn(viandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera,TipoEvento.RETIRO)).thenReturn(quedanPocasViandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera,TipoEvento.INGRESO)).thenReturn(hayMuchasViandas);

    notificadorMock.when(() -> Notificador.notificar(interesadosEnPocasViandas,quedanPocasViandas.getMensaje())).thenAnswer(invocationOnMock -> null);
    notificadorMock.when(() -> Notificador.notificar(interesadosEnMuchasViandas,quedanPocasViandas.getMensaje())).thenAnswer(invocationOnMock -> null);


    administradorDeEventos.setSuscripcionesRepository(suscripcionesRepositoryMock);
    administradorDeEventos.setViandaRepository(viandaRepositoryMock);


    vianda1.moverA(heladera);

    // Como para mover una vianda hay que retirar de una e ingresar de otra se ejecutan los dos eventos
    notificadorMock.verify(() -> Notificador.notificar(interesadosEnPocasViandas,quedanPocasViandas.getMensaje()));
    notificadorMock.verify(() -> Notificador.notificar(interesadosEnMuchasViandas,hayMuchasViandas.getMensaje()));
    notificadorMock.verify(() -> Notificador.notificar(suscriptores,huboDesperfecto.getMensaje()),times(0));

  }

  @Test @DisplayName("Cuando se reporta incidente se notifica a todos los suscriptores")
  public void seEnviaNotificacionPorDesperfecto() {
    Alerta alerta = new Alerta(heladera,"descripcion de alerta", TipoAlerta.ALERTA_FRAUDE);

    when(viandaRepositoryMock.buscarPorHeladera(heladera)).thenReturn(viandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera, TipoEvento.INCIDENTE)).thenReturn(huboDesperfecto);
    notificadorMock.when(() -> Notificador.notificar(suscriptores,huboDesperfecto.getMensaje())).thenAnswer(invocationOnMock -> null);

    administradorDeEventos.setSuscripcionesRepository(suscripcionesRepositoryMock);
    administradorDeEventos.setViandaRepository(viandaRepositoryMock);

    heladera.reportarIncidente(alerta);
    notificadorMock.verify(() -> Notificador.notificar(interesadosEnPocasViandas,quedanPocasViandas.getMensaje()),times(0));
    notificadorMock.verify(() -> Notificador.notificar(interesadosEnMuchasViandas,hayMuchasViandas.getMensaje()),times(0));
    notificadorMock.verify(() -> Notificador.notificar(suscriptores,huboDesperfecto.getMensaje()));

  }
}
