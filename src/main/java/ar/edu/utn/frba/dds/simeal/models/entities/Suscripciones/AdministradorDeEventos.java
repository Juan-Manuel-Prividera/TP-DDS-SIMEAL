package ar.edu.utn.frba.dds.simeal.models.entities.Suscripciones;

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

  // Cuando se ejecute un retirar o moverA se ejecuta este metodo
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    List<Suscriptor> suscriptores;
    List<Suscripcion> suscripciones = new ArrayList<>();

    // Sacamos del repositorio todas las suscripciones asociadas a la heladera
    // que dispara el evento
    suscripciones = suscripcionesRepository.buscarPor((Heladera) evt.getOldValue());


    for (Suscripcion suscripcion : suscripciones) {
      // Obtenemos a los suscriptores interesados en este evento particular
      suscriptores = new ArrayList<>(suscripcion
          .obtenerInteresados(cantidadViandasHeladera((Heladera) evt.getOldValue())));

      // Se notifica a los suscriptores del evento ocurrido utilizando el mensaje
      // propio de cada suscripcion
      suscriptores
          .forEach(suscriptor -> suscriptor.recibirNotificacion(suscripcion.getMensaje()));

    }
  }

  public int cantidadViandasHeladera(Heladera heladera) {
    List<Vianda> viandas = viandaRepository.buscarPorHeladera(heladera);
    return viandas.size();
  }
}
