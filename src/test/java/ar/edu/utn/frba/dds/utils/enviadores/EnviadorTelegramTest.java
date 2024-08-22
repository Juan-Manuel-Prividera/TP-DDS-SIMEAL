package ar.edu.utn.frba.dds.utils.enviadores;

import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.telegram.EnviadorTelegram;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.telegram.TelegramMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static org.mockito.Mockito.*;

public class EnviadorTelegramTest {
  Mensaje mensajePrueba = new Mensaje("¡Bienvenido a Simeal!");
  TelegramBotsApi telegramBotsApi;
  EnviadorTelegram enviadorTelegram;
  Long chatIdPrueba = 6072243064L;
  SendMessage messageMock;
  TelegramMessage telegramMessageMock;

  @BeforeEach
  public void init() throws TelegramApiException {
    enviadorTelegram = spy(EnviadorTelegram.class);

    telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    telegramBotsApi.registerBot(enviadorTelegram);

    messageMock = mock(SendMessage.class);
    telegramMessageMock = mock(TelegramMessage.class);

    enviadorTelegram.addChatId(chatIdPrueba);
    enviadorTelegram.setTelegramMessage(telegramMessageMock);

  }

/*
  @Test
  public void envioMensajePosta() throws TelegramApiException {
    EnviadorTelegram enviadorTelegramPosta = new EnviadorTelegram();
    enviadorTelegramPosta.addChatId(chatIdPrueba);

    telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    telegramBotsApi.registerBot(enviadorTelegramPosta);

    enviadorTelegramPosta.enviar(new Mensaje("Mensaje de prueba de Telegram :)"));

  }
*/

  @Test @DisplayName("Se ejecuta correctamente el envio el mensaje")
  public void envioMensajeTest() throws TelegramApiException {
    when(telegramMessageMock.getSendMessage(chatIdPrueba,mensajePrueba))
        .thenReturn(messageMock);

    enviadorTelegram.notificar(String.valueOf(chatIdPrueba),mensajePrueba);
    verify(enviadorTelegram, times(1)).execute(messageMock);
  }

  @Test @DisplayName("Test creacion mensaje de clase TelegramMessage")
  void creacionMessage() throws TelegramApiException {
    when(telegramMessageMock.getSendMessage(chatIdPrueba,mensajePrueba))
        .thenReturn(messageMock);

    enviadorTelegram.notificar(String.valueOf(chatIdPrueba),mensajePrueba);
    verify(telegramMessageMock, times(1))
        .getSendMessage(chatIdPrueba,mensajePrueba);
  }
}
