package ar.edu.utn.frba.dds.simeal.utils.notificaciones;

import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;

import java.util.List;

public class Notificador {


  public static void notificar(List<? extends ReceptorDeNotificaciones> receptores, Mensaje mensaje) {
    receptores.forEach(r -> notificar(r,mensaje));
  }
  public static void notificar(ReceptorDeNotificaciones receptor, Mensaje mensaje) {
    Logger.debug("Notificacion enviada a: " + receptor.getClass());
    receptor.recibirNotificacion(mensaje);
  }
}
