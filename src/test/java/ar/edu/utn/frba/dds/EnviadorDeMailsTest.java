package ar.edu.utn.frba.dds;

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

    @BeforeEach
    public void setUp(){
        enviadorDeMails = new EnviadorDeMails("3025","localhost",true);
        greenMail = new GreenMail(new ServerSetup(3025,"localhost","smtp"));
        greenMail.setUser("jprividera@frba.utn.edu.ar","bsmn ctyt qbug stah");
        greenMail.start();

        props = new Properties();
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", "3025");
    }

    @AfterEach
    public void after(){
        greenMail.stop();
    }

    @Test
    public void testSeEnvioElMail() throws MessagingException, IOException {
        Session session = Session.getInstance(props,null);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("jprividera@frba.utn.edu.ar"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("jprividera@gmail.com"));
        message.setSubject("hola");
        message.setText("hola cuerpo");

        enviadorDeMails.enviarMail("jmprividera@gmail.com","hola","hola cuerpo");

        greenMail.waitForIncomingEmail(5000,1);
        Message[] messages = greenMail.getReceivedMessages();

        Assertions.assertEquals(1, messages.length);
        Assertions.assertEquals("hola", messages[0].getSubject());
        Assertions.assertEquals("hola cuerpo", messages[0].getContent());


    }
}
