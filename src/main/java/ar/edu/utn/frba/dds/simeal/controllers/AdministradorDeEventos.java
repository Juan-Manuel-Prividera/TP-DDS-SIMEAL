package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.Evento;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.SuscripcionesRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.TipoRepo;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Notificador;
import lombok.Setter;

import java.util.List;

@Setter
public class AdministradorDeEventos {
  private ViandaRepository viandaRepository;
  private SuscripcionesRepository suscripcionesRepository;

  public AdministradorDeEventos(ViandaRepository viandaRepository, SuscripcionesRepository suscripcionesRepository) {
    this.viandaRepository = viandaRepository;
    this.suscripcionesRepository = suscripcionesRepository;
  }
  public AdministradorDeEventos() {
    this.viandaRepository = (ViandaRepository) ServiceLocator.getRepository(ViandaRepository.class);
    this.suscripcionesRepository = (SuscripcionesRepository) ServiceLocator.getRepository(SuscripcionesRepository.class);
  }

  public void huboUnEvento(Evento evento) {
    Heladera heladeraEvento = evento.getHeladeraAfectada();
    List<Suscripcion> suscripciones = suscripcionesRepository.buscarPor(heladeraEvento);
    List<Colaborador> interesados = obtenerInteresados(suscripciones, evento);
    if(!interesados.isEmpty())
      notificarInteresados(interesados, suscripciones.get(0).getNotificacion().getMensaje(heladeraEvento));
  }

  private int cantidadViandasHeladera(Heladera heladera) {
    List<Vianda> viandas = viandaRepository.buscarPorHeladera(heladera);
    return viandas.size();
  }

  private void notificarInteresados(List<Colaborador> interesados, Mensaje mensaje) {
    Notificador.notificar(interesados, mensaje);
  }

  public List<Colaborador> obtenerInteresados(List<Suscripcion> suscripciones, Evento evento) {
    return suscripciones.stream()
      .filter(s -> s.interesaEsteEvento(evento.getTipoEvento(),cantidadViandasHeladera(evento.getHeladeraAfectada())))
      .map(Suscripcion::getSuscriptor)
      .toList();
  }
}
