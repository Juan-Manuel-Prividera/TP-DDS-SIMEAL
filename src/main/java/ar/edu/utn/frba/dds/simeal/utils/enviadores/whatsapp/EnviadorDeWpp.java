package ar.edu.utn.frba.dds.simeal.utils.enviadores.whatsapp;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.MessageCreator;
import lombok.Setter;


public class EnviadorDeWpp {
  private final String accountSid; // Clave de Twilio
  private final String authToken; // Token de api de Twilio
  private final String numeroWpp; // El numero desde el que se envian los wpp

  private static EnviadorDeWpp instancia;

  // Este setter NUNCA se deberia llamar fuera de un TEST es solo para test
  @Setter
  private MyMessageCreator myMessageCreator;


  public static EnviadorDeWpp getInstance() {
    if (instancia == null) {
      instancia = new EnviadorDeWpp();
    }
    return instancia;
  }

  private EnviadorDeWpp() {
    // Leemos del application properties las credenciales de Twilio
    ConfigReader configReader = new ConfigReader();
    this.accountSid = configReader.getProperty("account.sid");
    this.authToken = configReader.getProperty("auth.token");
    this.numeroWpp = configReader.getProperty("phone.number");
    Twilio.init(accountSid, authToken);

    this.myMessageCreator = new MyMessageCreator();
  }

  public void enviar(String destinatario, Mensaje mensaje) {
    try {
      MessageCreator messageCreator =
          myMessageCreator.getMessageCreator(destinatario, numeroWpp, mensaje);

      // Este metodo directamente envia el mensaje
      messageCreator.create();
    } catch (Exception e) {
      System.err.println("Error al enviar el mensaje: " + e.getMessage());
    }
  }
}
