package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.ReceptorDeNotificaciones;

import java.util.List;

public class Notificador {

  public void notificar(List<ReceptorDeNotificaciones> receptores, Mensaje mensaje) {
    receptores.parallelStream().forEach(s -> s.recibirNotificacion(mensaje));
  }

}
