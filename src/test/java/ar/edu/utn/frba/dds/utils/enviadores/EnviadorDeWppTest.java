package ar.edu.utn.frba.dds.utils.enviadores;

import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.whatsapp.EnviadorDeWpp;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.whatsapp.MyMessageCreator;
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
  String phoneNumber;

  @BeforeEach
  public void init() {
    enviadorDeWpp = EnviadorDeWpp.getInstance();
    configReader = new ConfigReader();
    mensajePrueba = new Mensaje("hola");
    phoneNumber = configReader.getProperty("phone.number");

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
