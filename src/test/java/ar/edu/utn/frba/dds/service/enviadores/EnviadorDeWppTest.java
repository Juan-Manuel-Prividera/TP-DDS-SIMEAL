package ar.edu.utn.frba.dds.service.enviadores;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.ConfigReader;
import ar.edu.utn.frba.dds.simeal.service.enviadores.whatsapp.EnviadorDeWpp;
import ar.edu.utn.frba.dds.simeal.service.enviadores.whatsapp.MyMessageCreator;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


public class EnviadorDeWppTest {
  EnviadorDeWpp enviadorDeWpp;
  ConfigReader configReader;
  MessageCreator messageCreatorMock;
  Message messageMock;
  MyMessageCreator myMessageCreatorMock;


  Mensaje mensajePrueba;
  String DESTINATARIO = "5491169702930";
  String phoneNumber = configReader.getProperty("phone.number");

  @BeforeEach
  public void init() {
    enviadorDeWpp = EnviadorDeWpp.getInstance();
    configReader = new ConfigReader();
    mensajePrueba = new Mensaje("hola");

    messageMock = mock(Message.class);
    messageCreatorMock = mock(MessageCreator.class);
    myMessageCreatorMock = mock(MyMessageCreator.class);

    enviadorDeWpp.setMyMessageCreator(myMessageCreatorMock);
  }

  @Test
  public void enviaWppTest() {
    when(myMessageCreatorMock
        .getMessageCreator(DESTINATARIO, phoneNumber, mensajePrueba))
        .thenReturn(messageCreatorMock);
    when(messageCreatorMock.create()).thenReturn(messageMock);

    enviadorDeWpp.enviar(DESTINATARIO, mensajePrueba);

    // Que se llame al .create ya nos garantiza que se envie el Wpp
    verify(messageCreatorMock, times(1)).create();
  }

  @Test
  public void creacionMensajeTest() {
    when(myMessageCreatorMock
        .getMessageCreator(DESTINATARIO,phoneNumber, mensajePrueba))
        .thenReturn(messageCreatorMock);
    when(messageCreatorMock.create()).thenReturn(messageMock);

    enviadorDeWpp.enviar(DESTINATARIO, mensajePrueba);

    verify(myMessageCreatorMock, times(1))
        .getMessageCreator(DESTINATARIO,phoneNumber, mensajePrueba);
  }
}
