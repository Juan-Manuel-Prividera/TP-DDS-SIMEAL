package ar.edu.utn.frba.dds.simeal.utils.notificaciones.whatsapp;

import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.Setter;

// Esta clase nos permite tener por separado el mensaje de Wpp, asi lo podemos mockear :)
@Setter
public class MyMessageCreator {
  private MessageCreator messageCreator;

  public MessageCreator getMessageCreator(String destinatario, String numeroWpp, Mensaje mensaje) {
    ConfigReader configReader = new ConfigReader();
    System.out.println("Account SID: " +configReader.getProperty("account.sid"));
    System.out.println("Destinatario: " + destinatario);
    System.out.println("NumeroWpp: " + numeroWpp);
    System.out.println("Mensaje: " + mensaje.getMensaje());
    messageCreator = Message.creator(
        configReader.getProperty("account.sid"),
        new PhoneNumber("whatsapp:+" + destinatario),
        new PhoneNumber("whatsapp:+" + numeroWpp),
        mensaje.getMensaje());
    if (messageCreator == null) {
      System.out.println("hola");
    }
    return messageCreator;
  }

}
