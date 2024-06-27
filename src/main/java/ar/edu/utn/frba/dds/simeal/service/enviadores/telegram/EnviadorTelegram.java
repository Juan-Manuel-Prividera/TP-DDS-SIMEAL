package ar.edu.utn.frba.dds.simeal.service.enviadores.telegram;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.ConfigReader;
import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EnviadorTelegram extends TelegramLongPollingBot {

  // Este setter es para poder testear, NUNCA se deberia llamar desde otro lugar que no sea test
  @Setter
  private TelegramMessage telegramMessage;
  private ConfigReader configReader;
  private List<Long> chatIds;

  private static EnviadorTelegram instance;
  public static EnviadorTelegram getInstance() {
    if(instance == null)
      return new EnviadorTelegram();
    else
      return instance;
  }

  private EnviadorTelegram() {
    telegramMessage = new TelegramMessage();
    chatIds = new ArrayList<>();
    configReader = new ConfigReader();
  }

  // Recibe un nuevo mensaje "update" para iniciar el chat, toma el id del chat y lo guarda
  @Override
  public void onUpdateReceived(Update update) {
    Message message = update.getMessage();
    if (Objects.equals(message.getText(), "/start") && !chatIds.contains(message.getChatId())) {
      chatIds.add(message.getChatId());
      enviar(new Mensaje("¡Bienvenido a Simeal!"));
    }
  }

  // Envia un mensaje a todos los chats que tenga registrados
  public void enviar(Mensaje mensaje) {
    chatIds.forEach(id -> {
      try {
        // Ejecutar el mensaje es directamente enviarlo
        execute(telegramMessage.getSendMessage(id, mensaje));
      } catch (TelegramApiException e) {
        System.out.println("Error al enviar el mensaje: " + e.getMessage());
        throw new RuntimeException(e);
      }
    });
  }


  // Estos dos metodos los pide Telegram asi que se los damos :)
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
