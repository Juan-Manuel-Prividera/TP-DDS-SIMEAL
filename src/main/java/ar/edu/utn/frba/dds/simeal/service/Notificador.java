package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;

import java.util.List;

public class Notificador {

  public void notificar(List<Colaborador> suscriptores, Mensaje mensaje) {
    suscriptores.parallelStream().forEach(s -> s.recibirNotificacion(mensaje));
  }
}
