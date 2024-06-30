package ar.edu.utn.frba.dds.simeal.models.entities.eventos;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.SuscripcionesRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import ar.edu.utn.frba.dds.simeal.service.Notificador;
import lombok.Setter;

import java.util.List;

@Setter
public class AdministradorDeEventos {
  private ViandaRepository viandaRepository = ViandaRepository.getInstance();
  private SuscripcionesRepository suscripcionesRepository = SuscripcionesRepository.getInstance();

  // Cuando se ejecute un retirar o moverA o reportarIncidente se ejecuta este metodo
  public void huboUnEvento(Evento evento) {
    Heladera heladeraEvento = evento.getHeladeraAfectada();
    List<Suscripcion> suscripciones = suscripcionesRepository.buscarPor(heladeraEvento, evento.getTipoEvento());
    List<Colaborador> interesados = evento.getNotificacion().obtenerInteresados(suscripciones,cantidadViandasHeladera(heladeraEvento));
    notificarInteresados(interesados,evento.getNotificacion().getMensaje());
  }

  private int cantidadViandasHeladera(Heladera heladera) {
    List<Vianda> viandas = viandaRepository.buscarPorHeladera(heladera);
    return viandas.size();
  }

  private void notificarInteresados(List<Colaborador> interesados, Mensaje mensaje) {
    Notificador.notificar(interesados, mensaje);
  }
}
