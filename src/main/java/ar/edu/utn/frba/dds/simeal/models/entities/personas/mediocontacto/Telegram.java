package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.enviadores.telegram.EnviadorTelegram;
import ar.edu.utn.frba.dds.simeal.service.enviadores.whatsapp.EnviadorDeWpp;

public class Telegram {
  private String chatid;
  private EnviadorTelegram enviador;

  public Telegram(String chatid, EnviadorTelegram enviadorTelegram){
    this.chatid = chatid;
    this.enviador = enviadorTelegram;
  }

  public void enviar(Mensaje mensaje) {
    enviador.enviar(mensaje);
  }
}
