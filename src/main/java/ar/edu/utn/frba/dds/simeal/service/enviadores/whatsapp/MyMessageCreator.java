package ar.edu.utn.frba.dds.simeal.service.enviadores.whatsapp;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.Setter;

// Esta clase nos permite tener por separado el mensaje de Wpp, asi lo podemos mockear :)
@Setter
public class MyMessageCreator {
  MessageCreator messageCreator;

  public MessageCreator getMessageCreator(String destinatario, String numeroWpp, Mensaje mensaje) {
    return this.messageCreator = Message.creator(
        new PhoneNumber("whatsapp:+" + destinatario),
        new PhoneNumber("whatsapp:+" + numeroWpp),
        mensaje.getMensaje());
  }

}
