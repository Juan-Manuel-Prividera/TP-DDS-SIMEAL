package ar.edu.utn.frba.dds.simeal.service.enviadores.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class TelegramMessage {
  private SendMessage sendMessage;



  public SendMessage getSendMessage(Long chatId, String text) {
    sendMessage = new SendMessage();
    this.sendMessage.setChatId(chatId);
    this.sendMessage.setText(text);
    return this.sendMessage;
  }
}
