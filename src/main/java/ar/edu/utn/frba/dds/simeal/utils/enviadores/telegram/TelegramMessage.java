package ar.edu.utn.frba.dds.simeal.utils.enviadores.telegram;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


// Esta clase es el mensaje que se envia por telegram, arma un mensaje segun un chatID y un string
// Podria hacerce directo en EnviadorTelegram si, pero teniendolo asi nos permite testearlo, de la
// forma no podriamos sin enviar un mensaje en cada test
public class TelegramMessage {
  private SendMessage sendMessage;



  public SendMessage getSendMessage(Long chatId, Mensaje mensaje) {
    sendMessage = new SendMessage();
    this.sendMessage.setChatId(chatId);
    this.sendMessage.setText(mensaje.getMensaje());
    return this.sendMessage;
  }
}
