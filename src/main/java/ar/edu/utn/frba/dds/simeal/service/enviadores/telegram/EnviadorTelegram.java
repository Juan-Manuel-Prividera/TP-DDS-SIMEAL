package ar.edu.utn.frba.dds.simeal.service.enviadores.telegram;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.ConfigReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class EnviadorTelegram extends TelegramLongPollingBot {
  @Setter
  TelegramMessage telegramMessage;
  ConfigReader configReader;
  List<Long> chatIds;


  public EnviadorTelegram() {
    telegramMessage = new TelegramMessage();
    chatIds = new ArrayList<>();
    configReader = new ConfigReader();
  }

  @Override
  public void onUpdateReceived(Update update) {
    Message message = update.getMessage();
    if (Objects.equals(message.getText(), "/start") && !chatIds.contains(message.getChatId())) {
      chatIds.add(message.getChatId());
      enviar(new Mensaje("¡Bienvenido a Simeal!"));
    }
  }

  public void enviar(Mensaje mensaje) {
    chatIds.forEach(id -> {
      try {
        execute(telegramMessage.getSendMessage(id, mensaje.getMensaje()));
      } catch (TelegramApiException e) {
        System.out.println("Error al enviar el mensaje: " + e.getMessage());
        throw new RuntimeException(e);
      }
    });
  }

  @Override
  public String getBotUsername() {
    return configReader.getProperty("bot.username");
  }

  @Override
  public String getBotToken() {
    return configReader.getProperty("bot.token");
  }

  public void addChatId(Long chatId) {
    chatIds.add(chatId);
  }
}
