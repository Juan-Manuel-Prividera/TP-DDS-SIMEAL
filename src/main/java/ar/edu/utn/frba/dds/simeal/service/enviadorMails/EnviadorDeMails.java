package ar.edu.utn.frba.dds.simeal.service.enviadorMails;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;


public class EnviadorDeMails {
    private final String useremail="jprividera@frba.utn.edu.ar";
    private final String password="bsmn ctyt qbug stah";
    private final String host; // smtp.gmail.com
    private final String port; // 587
    private final boolean auth;

    public EnviadorDeMails() {
        this.auth = true;
        this.host = "smtp.gmail.com";
        this.port = "587";
    }

    // Se usa en el test para probar en un servidor local
    public EnviadorDeMails(String port, String host, boolean auth){
        this.host = host;
        this.port = port;
        this.auth = auth;
    }


    public boolean enviarMail(String destinatario, String asunto, String contenido) {
        Properties properties = new Properties();
        properties.put("mail.smtp.user", useremail); // Nombre de usuario que envia el mail
        properties.put("mail.smtp.host", host); // Host de gmail
        properties.put("mail.smtp.port", port); // Puerto de Gmail
        properties.put("mail.smtp.auth", String.valueOf(auth)); // Requiere autenticar para conectarse
        properties.put("mail.smtp.starttls.enable", "true");

        Session session;
        try {
            if (auth) {
                // Creacion de la sesion
                session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(useremail, password);
                    }
                });
                session.setDebug(true);
            } else{
                session = Session.getDefaultInstance(properties, null);
            }

            // Creacion del mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(useremail)); // Origen del correo
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario del correo
            message.setSubject(asunto); // Asunto del mail
            message.setText(contenido); // Cuerpo del mail

            // Envio del mail
            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            return true;
        } catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }
}
