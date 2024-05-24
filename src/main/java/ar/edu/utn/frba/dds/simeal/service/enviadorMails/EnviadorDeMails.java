package ar.edu.utn.frba.dds.simeal.service.enviadorMails;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.ConfigReader;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Setter;

import java.util.Properties;

@Setter
public class EnviadorDeMails implements Enviador {
    private String useremail;
    private String password;
    private String host; // smtp.gmail.com
    private String port; // 587
    private boolean auth;

    // Por defecto usar Gmail
    public EnviadorDeMails(ConfigReader configReader) {
        init(configReader,"gmail");
    }
    // Para hacer los test o si se quiere usar otro proveedor
    public EnviadorDeMails(ConfigReader configReader, String proveedor) {
        init(configReader, proveedor);
    }

    private void init(ConfigReader configReader, String proveedor) {
        this.host = configReader.getProperty(proveedor+".host");
        this.port = configReader.getProperty(proveedor+".port");
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
            session.setDebug(true);

            // Creacion del mensaje
            Message message = crearMessage(session,mensaje,destinatario);

            // Envio del mail
            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }

    private Message crearMessage(Session session, Mensaje mensaje, String destinatario) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(useremail)); // Origen del correo
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario del correo
        message.setSubject(mensaje.getAsunto()); // Asunto del mail
        message.setText(mensaje.getMensaje()); // Cuerpo del mail

        return message;
    }
}
