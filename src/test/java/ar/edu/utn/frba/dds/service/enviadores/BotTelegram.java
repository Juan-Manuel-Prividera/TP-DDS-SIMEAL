package ar.edu.utn.frba.dds.service.enviadores;

import ar.edu.utn.frba.dds.simeal.utils.enviadores.telegram.EnviadorTelegram;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotTelegram {
  public static void main(String[] args) throws TelegramApiException {
    TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
    api.registerBot(EnviadorTelegram.getInstance());
  }
}
