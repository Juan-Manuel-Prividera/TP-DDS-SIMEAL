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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

  List<Suscriptor> suscriptores;
  Suscriptor suscriptor1;
  Suscriptor suscriptor2;
  Suscriptor suscriptor3;

  List<Suscriptor> interesadosEnPocasViandas;
  List<Suscriptor> interesadosEnMuchasViandas;
  List<Suscriptor> interesadosEnDesperfectos;

  SuscripcionesRepository suscripcionesRepositoryMock;
  ViandaRepository viandaRepositoryMock;
  Notificador notificadorMock;

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

    notificadorMock = mock(Notificador.class);
    viandaRepositoryMock = mock(ViandaRepository.class);
    suscripcionesRepositoryMock = mock(SuscripcionesRepository.class);


    interesadosEnPocasViandas.add(suscriptor1);
    interesadosEnPocasViandas.add(suscriptor2);

    interesadosEnMuchasViandas.add(suscriptor1);
    interesadosEnMuchasViandas.add(suscriptor2);

  }

  @Test @DisplayName("Quedan 3 viandas en la heladera => Se notifica a los suscriptores con cantidad critica 3")
  public void seEnviaLaNotificacionPorPocasViandas() {
    viandas.remove(vianda1);
    when(viandaRepositoryMock.buscarPorHeladera(heladera)).thenReturn(viandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera, TipoEvento.RETIRO)).thenReturn(quedanPocasViandas);
    doNothing().when(notificadorMock).notificar(interesadosEnPocasViandas, quedanPocasViandas.getMensaje());

    administradorDeEventos.setSuscripcionesRepository(suscripcionesRepositoryMock);
    administradorDeEventos.setViandaRepository(viandaRepositoryMock);
    administradorDeEventos.setNotificador(notificadorMock);


    vianda1.retirar();

    verify(notificadorMock).notificar(interesadosEnPocasViandas,quedanPocasViandas.getMensaje());
    verify(notificadorMock, never()).notificar(interesadosEnMuchasViandas,hayMuchasViandas.getMensaje());
    verify(notificadorMock, never()).notificar(suscriptores,huboDesperfecto.getMensaje());
  }

  @Test @DisplayName("Cuando se hace un moverA se ejecuta tambien el retirar")
  public void seEnviaNotificacionPorMuchasViandas() {
    viandas.remove(vianda1);
    when(viandaRepositoryMock.buscarPorHeladera(heladera)).thenReturn(viandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera,TipoEvento.RETIRO)).thenReturn(quedanPocasViandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera,TipoEvento.INGRESO)).thenReturn(hayMuchasViandas);
    doNothing().when(notificadorMock).notificar(interesadosEnPocasViandas, quedanPocasViandas.getMensaje());

    administradorDeEventos.setSuscripcionesRepository(suscripcionesRepositoryMock);
    administradorDeEventos.setViandaRepository(viandaRepositoryMock);
    administradorDeEventos.setNotificador(notificadorMock);


    vianda1.moverA(heladera);

    // Como para mover una vianda hay que retirar de una e ingresar de otra se ejecutan los dos eventos

    verify(notificadorMock).notificar(interesadosEnPocasViandas,quedanPocasViandas.getMensaje());
    verify(notificadorMock).notificar(interesadosEnMuchasViandas,hayMuchasViandas.getMensaje());
    verify(notificadorMock, never()).notificar(suscriptores,huboDesperfecto.getMensaje());
  }

  @Test @DisplayName("Cuando se reporta incidente se notifica a todos los suscriptores")
  public void seEnviaNotificacionPorDesperfecto() {
    Alerta alerta = new Alerta(heladera,"descripcion de alerta", TipoAlerta.ALERTA_FRAUDE);

    when(viandaRepositoryMock.buscarPorHeladera(heladera)).thenReturn(viandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera, TipoEvento.INCIDENTE)).thenReturn(huboDesperfecto);
    doNothing().when(notificadorMock).notificar(suscriptores, huboDesperfecto.getMensaje());

    administradorDeEventos.setSuscripcionesRepository(suscripcionesRepositoryMock);
    administradorDeEventos.setViandaRepository(viandaRepositoryMock);
    administradorDeEventos.setNotificador(notificadorMock);

    heladera.reportarIncidente(alerta);

    verify(notificadorMock, never()).notificar(interesadosEnPocasViandas,quedanPocasViandas.getMensaje());
    verify(notificadorMock, never()).notificar(interesadosEnMuchasViandas,hayMuchasViandas.getMensaje());
    verify(notificadorMock).notificar(suscriptores,huboDesperfecto.getMensaje());
  }
}
