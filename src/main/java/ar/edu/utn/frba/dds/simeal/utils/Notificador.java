package ar.edu.utn.frba.dds.simeal.utils;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.ReceptorDeNotificaciones;

import java.util.List;

public class Notificador {


  public static void notificar(List<? extends ReceptorDeNotificaciones> receptores, Mensaje mensaje) {
    receptores.forEach(r -> notificar(r,mensaje));
  }
  public static void notificar(ReceptorDeNotificaciones receptor, Mensaje mensaje) {
    receptor.recibirNotificacion(mensaje);
  }
}
