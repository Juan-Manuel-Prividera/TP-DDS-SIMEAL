package ar.edu.utn.frba.dds.simeal.utils.notificaciones;

import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;

import java.util.Properties;




@Getter
public class EnviadorDeMails {
  private final String useremail;
  private final String password;
  private final String host; // smtp.gmail.com
  private final String port; // 587
  private final boolean auth;

  private static EnviadorDeMails instancia = null;


  public static EnviadorDeMails getInstancia() {
    return getInstancia( "gmail");
  }

  public static EnviadorDeMails getInstancia(String proveedor) {
    ConfigReader configReader = new ConfigReader();
    if (instancia == null
        || !instancia.port.equals(configReader.getProperty(proveedor + ".port"))) {
      instancia = new EnviadorDeMails(configReader, proveedor);
    }
    return instancia;
  }
  public EnviadorDeMails() {
    ConfigReader configReader = new ConfigReader();
    this.host = configReader.getProperty("gmail.host");
    this.port = configReader.getProperty("gmail.port");
    this.useremail = configReader.getProperty("user.email");
    this.password = configReader.getProperty("app.password");
    this.auth = true;
  }

  private EnviadorDeMails(ConfigReader configReader, String proveedor) {
    this.host = configReader.getProperty(proveedor + ".host");
    this.port = configReader.getProperty(proveedor + ".port");
    this.useremail = configReader.getProperty("user.email");
    this.password = configReader.getProperty("app.password");
    this.auth = true;
  }


  public void enviar(String destinatario, Mensaje mensaje) {
    Properties properties = getProperties();
    Session session;

    try {
      // Creacion de la sesion
      session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(useremail, password);
        }
      });

      // session.setDebug(true);

      // Creacion del mensaje
      MimeMessage message = crearMessage(session, mensaje, destinatario);

      // Envio del mail
      Transport transport = session.getTransport("smtp");
      transport.connect();
      transport.sendMessage(message, message.getAllRecipients());
      transport.close();

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  private MimeMessage crearMessage(Session session, Mensaje mensaje, String destinatario)
      throws MessagingException {

    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(useremail));
    message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(destinatario));
    message.setSubject(mensaje.getAsunto());
    message.setText(mensaje.getMensaje());

    return message;
  }

  private Properties getProperties() {
    Properties properties = new Properties();
    properties.put("mail.smtp.user", useremail); // Nombre de usuario que envia el mail
    properties.put("mail.smtp.host", host); // Host de gmail
    properties.put("mail.smtp.port", port); // Puerto de Gmail
    properties.put("mail.smtp.auth", String.valueOf(auth)); // Requiere autenticar para conectarse
    properties.put("mail.smtp.starttls.enable", "true");

    return properties;
  }
}
