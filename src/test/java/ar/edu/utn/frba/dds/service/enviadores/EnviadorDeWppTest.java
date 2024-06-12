package ar.edu.utn.frba.dds.service.enviadores;

import static org.mockito.Mockito.*;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.ConfigReader;
import ar.edu.utn.frba.dds.simeal.service.enviadores.whatsapp.EnviadorDeWpp;
import ar.edu.utn.frba.dds.simeal.service.enviadores.whatsapp.MyMessageCreator;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class EnviadorDeWppTest {
  EnviadorDeWpp enviadorDeWpp;
  ConfigReader configReader;
  Mensaje mensajePrueba;

  @BeforeEach
  public void init() {
    enviadorDeWpp = EnviadorDeWpp.getInstance();
    configReader = new ConfigReader();
    mensajePrueba = new Mensaje("hola", null);
  }

  @Test
  public void enviadorDeWppTest() {
    Message messageMock = mock(Message.class);
    MessageCreator messageCreatorMock = mock(MessageCreator.class);
    MyMessageCreator myMessageCreatorMock = mock(MyMessageCreator.class);

    when(myMessageCreatorMock
        .getMessageCreator("5491169702930",configReader.getProperty("phone.number") , mensajePrueba))
        .thenReturn(messageCreatorMock);

    when(messageCreatorMock.create()).thenReturn(messageMock);

    enviadorDeWpp.setMyMessageCreator(myMessageCreatorMock);
    enviadorDeWpp.enviar("5491169702930", mensajePrueba);

    verify(myMessageCreatorMock, times(1))
        .getMessageCreator("5491169702930",configReader.getProperty("phone.number"), mensajePrueba);

    // Que se llame al .create ya nos garantiza que se envie el Wpp
    verify(messageCreatorMock, times(1)).create();
  }

}
