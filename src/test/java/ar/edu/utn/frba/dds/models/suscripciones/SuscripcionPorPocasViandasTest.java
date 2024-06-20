package ar.edu.utn.frba.dds.models.suscripciones;

import static org.mockito.Mockito.*;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.TipoAlerta;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.*;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.SuscripcionesRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SuscripcionPorPocasViandasTest {
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
    heladera = new Heladera(new Ubicacion("lalala",123));
    vianda1 = new Vianda(heladera);
    vianda2 = new Vianda(heladera);
    vianda3 = new Vianda(heladera);
    vianda4 = new Vianda(heladera);

    viandas = new ArrayList<>();
    viandas.add(vianda1);
    viandas.add(vianda2);
    viandas.add(vianda3);
    viandas.add(vianda4);

    interesadosEnPocasViandas = new ArrayList<>();
    interesadosEnMuchasViandas = new ArrayList<>();
    interesadosEnDesperfectos = new ArrayList<>();

    suscriptor1 = new Colaborador(1);
    suscriptor2 = new Colaborador(2);
    suscriptor3 = new Colaborador(4);

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

    administradorDeEventos = new AdministradorDeEventos();


  }

  @Test @DisplayName("Quedan 3 viandas en la heladera => Se notifica a los suscriptores con cantidad critica 3")
  public void seEnviaLaNotificacionPorPocasViandas() {
    List<Suscripcion> suscripcion = new ArrayList<>();
    suscripcion.add(quedanPocasViandas);
    interesadosEnPocasViandas.add(suscriptor1);
    interesadosEnPocasViandas.add(suscriptor2);

    interesadosEnMuchasViandas.add(suscriptor1);
    interesadosEnMuchasViandas.add(suscriptor2);

    viandas.remove(vianda1);
    when(viandaRepositoryMock.buscarPorHeladera(heladera)).thenReturn(viandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera, "retirar")).thenReturn(suscripcion);
    doNothing().when(notificadorMock).notificar(interesadosEnPocasViandas, quedanPocasViandas.getMensaje());

    administradorDeEventos.setSuscripcionesRepository(suscripcionesRepositoryMock);
    administradorDeEventos.setViandaRepository(viandaRepositoryMock);
    administradorDeEventos.setNotificador(notificadorMock);

    vianda1.agregarOyente(administradorDeEventos);

    vianda1.retirar();

    verify(notificadorMock).notificar(interesadosEnPocasViandas,quedanPocasViandas.getMensaje());
    verify(notificadorMock, never()).notificar(interesadosEnMuchasViandas,hayMuchasViandas.getMensaje());
    verify(notificadorMock, never()).notificar(suscriptores,huboDesperfecto.getMensaje());
  }

  @Test @DisplayName("Cuando se hace un moverA se ejecuta tambien el retirar")
  public void seEnviaNotificacionPorMuchasViandas() {
    List<Suscripcion> suscripcion = new ArrayList<>();
    suscripcion.add(hayMuchasViandas);
    List<Suscripcion> suscripcion2 = new ArrayList<>();
    suscripcion2.add(quedanPocasViandas);
    interesadosEnPocasViandas.add(suscriptor1);
    interesadosEnPocasViandas.add(suscriptor2);

    interesadosEnMuchasViandas.add(suscriptor1);
    interesadosEnMuchasViandas.add(suscriptor2);

    viandas.remove(vianda1);
    when(viandaRepositoryMock.buscarPorHeladera(heladera)).thenReturn(viandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera,"retirar")).thenReturn(suscripcion2);
    when(suscripcionesRepositoryMock.buscarPor(heladera, "ingresarA")).thenReturn(suscripcion);
    doNothing().when(notificadorMock).notificar(interesadosEnPocasViandas, quedanPocasViandas.getMensaje());

    administradorDeEventos.setSuscripcionesRepository(suscripcionesRepositoryMock);
    administradorDeEventos.setViandaRepository(viandaRepositoryMock);
    administradorDeEventos.setNotificador(notificadorMock);

    vianda1.agregarOyente(administradorDeEventos);

    vianda1.moverA(heladera);

    // Como para mover una vianda hay que retirar de una e ingresar de otra se ejecutan los dos eventos

    verify(notificadorMock).notificar(interesadosEnPocasViandas,quedanPocasViandas.getMensaje());
    verify(notificadorMock).notificar(interesadosEnMuchasViandas,hayMuchasViandas.getMensaje());
    verify(notificadorMock, never()).notificar(suscriptores,huboDesperfecto.getMensaje());
  }

  @Test @DisplayName("Cuando se reporta incidente se notifica a todos los suscriptores")
  public void seEnviaNotificacionPorDesperfecto() {
    List<Suscripcion> suscripcion = new ArrayList<>();
    suscripcion.add(huboDesperfecto);
    Alerta alerta = mock(Alerta.class);

    when(viandaRepositoryMock.buscarPorHeladera(heladera)).thenReturn(viandas);
    when(suscripcionesRepositoryMock.buscarPor(heladera, "incidente")).thenReturn(suscripcion);
    doNothing().when(notificadorMock).notificar(interesadosEnPocasViandas, quedanPocasViandas.getMensaje());
    when(alerta.getNotificacion()).thenReturn("gola");
    administradorDeEventos.setSuscripcionesRepository(suscripcionesRepositoryMock);
    administradorDeEventos.setViandaRepository(viandaRepositoryMock);
    administradorDeEventos.setNotificador(notificadorMock);


    heladera.agregarOyente(administradorDeEventos);
    heladera.reportarIncidente(alerta);

    verify(notificadorMock, never()).notificar(interesadosEnPocasViandas,quedanPocasViandas.getMensaje());
    verify(notificadorMock, never()).notificar(interesadosEnMuchasViandas,hayMuchasViandas.getMensaje());
    verify(notificadorMock).notificar(suscriptores,huboDesperfecto.getMensaje());
  }
}
