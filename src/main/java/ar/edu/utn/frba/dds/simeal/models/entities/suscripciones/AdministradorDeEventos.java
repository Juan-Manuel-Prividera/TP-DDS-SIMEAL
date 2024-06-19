package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.SuscripcionesRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

@Setter
public class AdministradorDeEventos implements PropertyChangeListener {
  private ViandaRepository viandaRepository;
  private SuscripcionesRepository suscripcionesRepository;
  private Notificador notificador = new Notificador();
  List<Suscriptor> suscriptores;
  List<Suscripcion> suscripciones = new ArrayList<>();

  // Cuando se ejecute un retirar o moverA o reportarIncidente se ejecuta este metodo
  @Override
  public void propertyChange(PropertyChangeEvent evento) {
    Heladera heladeraEvento = (Heladera) evento.getOldValue();
    suscripciones = suscripcionesRepository.buscarPor(heladeraEvento, evento.getPropertyName());

    for (Suscripcion suscripcion : suscripciones) {
      suscriptores = new ArrayList<>(
          suscripcion.obtenerInteresados(cantidadViandasHeladera(heladeraEvento))
      );

      notificarUnaSuscripcion(suscripcion, suscriptores);
    }
  }

  public int cantidadViandasHeladera(Heladera heladera) {
    List<Vianda> viandas = viandaRepository.buscarPorHeladera(heladera);
    return viandas.size();
  }

  public void notificarUnaSuscripcion(Suscripcion suscripcion, List<Suscriptor> suscriptores) {
    if (!suscriptores.isEmpty()) {
        notificador.notificar(suscriptores, suscripcion.getMensaje());
    }
  }
}
