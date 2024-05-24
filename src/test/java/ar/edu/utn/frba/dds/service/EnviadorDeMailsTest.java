package ar.edu.utn.frba.dds.service;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.medioContacto.Email;
import ar.edu.utn.frba.dds.simeal.service.ConfigReader;
import ar.edu.utn.frba.dds.simeal.service.enviadorMails.EnviadorDeMails;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

public class EnviadorDeMailsTest {
    EnviadorDeMails enviadorDeMails;
    GreenMail greenMail;
    Properties props;
    ConfigReader configReader;
    String greenmailHost;
    String greenmailPort;


    @BeforeEach
    public void setUp(){
        configReader = new ConfigReader("src/main/application.properties");
        greenmailPort = configReader.getProperty("greenmail.port");
        greenmailHost = configReader.getProperty("greenmail.host");

        enviadorDeMails = new EnviadorDeMails(configReader, "greenmail");

        greenMail = new GreenMail(new ServerSetup(3025,greenmailHost,"smtp"));
        greenMail.setUser(configReader.getProperty("user.email"),configReader.getProperty("app.password"));
        greenMail.start();

        props = new Properties();
        props.put("mail.smtp.host", greenmailHost);
        props.put("mail.smtp.port", greenmailPort);
    }

    @AfterEach
    public void after(){
        greenMail.stop();
    }

    @Test
    public void enviarMailPosta() {
        enviadorDeMails = new EnviadorDeMails(new ConfigReader("src/main/application.properties"));
        enviadorDeMails.enviar("jmprividera@gmail.com", new Mensaje("hola","hola"));
    }

    @Test
    public void testSeEnvioElMail() throws MessagingException, IOException {
        Session session = Session.getInstance(props,null);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(configReader.getProperty("user.email")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("jmprividera@gmail.com"));
        message.setSubject("hola");
        message.setText("hola cuerpo");

        Mensaje mensaje = new Mensaje("hola cuerpo", "hola");
        enviadorDeMails.enviar("jmprividera@gmail.com",mensaje);

        greenMail.waitForIncomingEmail(5000,1);
        Message[] messages = greenMail.getReceivedMessages();

        Assertions.assertEquals(1, messages.length);
        Assertions.assertEquals(mensaje.getAsunto(), messages[0].getSubject());
        Assertions.assertEquals(mensaje.getMensaje(), messages[0].getContent());


    }

    @Test
    public void envioNotificacionAColab() throws MessagingException, IOException {
        Colaborador colaborador = new Colaborador(new Documento(TipoDocumento.DNI,"12345678"),"Juan","Perez");
        colaborador.addMedioContacto(new Email("jperez@gmail.com",enviadorDeMails));
        Mensaje mensaje = new Mensaje("Hola juan", "Notificacion");
        colaborador.getMediosDeContacto().get(0).notificar(mensaje);

        greenMail.waitForIncomingEmail(5000,1);
        Message[] messages = greenMail.getReceivedMessages();

        Assertions.assertEquals(1, messages.length);
        Assertions.assertEquals(mensaje.getAsunto(), messages[0].getSubject());
        Assertions.assertEquals(mensaje.getMensaje(), messages[0].getContent());
    }
}
