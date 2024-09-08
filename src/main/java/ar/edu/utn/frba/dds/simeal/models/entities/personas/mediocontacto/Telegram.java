package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.telegram.EnviadorTelegram;


public class Telegram implements MedioContacto{
  private final EnviadorTelegram enviador;

  public Telegram(EnviadorTelegram enviadorTelegram){
    this.enviador = enviadorTelegram;
  }

  public void notificar(String destinatario, Mensaje mensaje) {
    enviador.notificar(destinatario, mensaje);
  }
}
