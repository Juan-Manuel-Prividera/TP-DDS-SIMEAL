package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.ReceptorDeNotificaciones;

import java.util.List;

public class Notificador {


  public void notificar(List<? extends ReceptorDeNotificaciones> receptores, Mensaje mensaje) {
    receptores.forEach(r -> notificar(r,mensaje));
  }
  public void notificar(ReceptorDeNotificaciones receptor, Mensaje mensaje) {
    receptor.recibirNotificacion(mensaje);
  }
}
