package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import java.util.List;

public class Notificador {

  public void notificar(List<Suscriptor> suscriptores, Mensaje mensaje) {
    suscriptores.parallelStream().forEach(s -> s.recibirNotificacion(mensaje));
  }
}
