package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.enviadores.telegram.EnviadorTelegram;
import ar.edu.utn.frba.dds.simeal.service.enviadores.whatsapp.EnviadorDeWpp;

public class Telegram {
  private EnviadorTelegram enviador;

  public Telegram(EnviadorTelegram enviadorTelegram){
    this.enviador = enviadorTelegram;
  }

  public void enviar(String destinatario, Mensaje mensaje) {
    enviador.notificar(destinatario, mensaje);
  }
}
