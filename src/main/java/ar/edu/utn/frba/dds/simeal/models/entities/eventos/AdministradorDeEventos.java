package ar.edu.utn.frba.dds.simeal.models.entities.eventos;

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
  private ViandaRepository viandaRepository;
  private SuscripcionesRepository suscripcionesRepository;
  private Notificador notificador = new Notificador();

  // Cuando se ejecute un retirar o moverA o reportarIncidente se ejecuta este metodo
  public void huboUnEvento(Evento evento) {
    Heladera heladeraEvento = (Heladera) evento.getHeladeraAfectada();
    Suscripcion suscripcion = suscripcionesRepository.buscarPor(heladeraEvento, evento.getTipoEvento());
    notificarUnaSuscripcion(suscripcion, suscripcion.obtenerInteresados(cantidadViandasHeladera(heladeraEvento)));
  }

  private int cantidadViandasHeladera(Heladera heladera) {
    List<Vianda> viandas = viandaRepository.buscarPorHeladera(heladera);
    return viandas.size();
  }

  private void notificarUnaSuscripcion(Suscripcion suscripcion, List<Colaborador> suscriptores) {
    if (!suscriptores.isEmpty()) {
        notificador.notificar(suscriptores, suscripcion.getMensaje());
    }
  }
}
