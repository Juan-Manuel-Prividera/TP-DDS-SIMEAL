package ar.edu.utn.frba.dds.simeal.service.enviadorMails;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.ConfigReader;
import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import lombok.Setter;



@Setter
public class EnviadorDeMails implements Enviador {
  private String useremail;
  private String password;
  private String host; // smtp.gmail.com
  private String port; // 587
  private boolean auth;

  private EnviadorDeMails instancia = null;
  // Por defecto usar Gmail
  public EnviadorDeMails(ConfigReader configReader) {
    init(configReader, "gmail");
  }

//  public EnviadorDeMails getInstancia(){
//    if(instancia == null){
//      return new EnviadorDeMails();
//    } else
//      return instancia;
//  }

  // Para hacer los test o si se quiere usar otro proveedor
  public EnviadorDeMails(ConfigReader configReader, String proveedor) {
    init(configReader, proveedor);
  }

  private void init(ConfigReader configReader, String proveedor) {
    this.host = configReader.getProperty(proveedor + ".host");
    this.port = configReader.getProperty(proveedor + ".port");
    this.useremail = configReader.getProperty("user.email");
    this.password = configReader.getProperty("app.password");
    this.auth = true;
  }

  public void enviar(String destinatario, Mensaje mensaje) {
    Properties properties = new Properties();
    properties.put("mail.smtp.user", useremail); // Nombre de usuario que envia el mail
    properties.put("mail.smtp.host", host); // Host de gmail
    properties.put("mail.smtp.port", port); // Puerto de Gmail
    properties.put("mail.smtp.auth", String.valueOf(auth)); // Requiere autenticar para conectarse
    properties.put("mail.smtp.starttls.enable", "true");

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
    // Origen del correo
    message.setFrom(new InternetAddress(useremail));
    // Destinatario del correo
    message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(destinatario));
    message.setSubject(mensaje.getAsunto());
    message.setText(mensaje.getMensaje());

    return message;
  }
}
