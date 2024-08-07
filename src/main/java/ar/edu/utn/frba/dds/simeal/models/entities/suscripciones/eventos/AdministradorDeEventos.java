package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.SuscripcionesRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import ar.edu.utn.frba.dds.simeal.service.Notificador;
import lombok.Setter;

import java.util.List;

@Setter
public class AdministradorDeEventos {
  private ViandaRepository viandaRepository = ViandaRepository.getInstance();
  private SuscripcionesRepository suscripcionesRepository = SuscripcionesRepository.getInstance();

  public void huboUnEvento(Evento evento) {
    if(evento.getHeladeraAfectada() == null)
      return;

    Heladera heladeraEvento = evento.getHeladeraAfectada();
    List<Suscripcion> suscripciones = suscripcionesRepository.buscarPor(heladeraEvento);
    List<Colaborador> interesados = suscripciones.stream()
            .filter(s -> s.interesaEsteEvento(evento.getTipoEvento(),cantidadViandasHeladera(heladeraEvento)))
            .map(Suscripcion::getSuscriptor)
            .toList();

    notificarInteresados(interesados, evento.getNotificacion().getMensaje());
  }

  private int cantidadViandasHeladera(Heladera heladera) {
    List<Vianda> viandas = viandaRepository.buscarPorHeladera(heladera);
    return viandas.size();
  }

  private void notificarInteresados(List<Colaborador> interesados, Mensaje mensaje) {
    Notificador.notificar(interesados, mensaje);
  }
}
