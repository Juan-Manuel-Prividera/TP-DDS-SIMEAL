package ar.edu.utn.frba.dds.utils.enviadores;

import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Email;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.EnviadorDeMails;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
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
    configReader = new ConfigReader();
    greenmailPort = configReader.getProperty("greenmail.port");
    greenmailHost = configReader.getProperty("greenmail.host");

    enviadorDeMails = EnviadorDeMails.getInstancia(configReader, "greenmail");

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
  public void testSeEnvioElMail() throws MessagingException, IOException {
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
    Colaborador colaborador =
        new Colaborador(new Documento(TipoDocumento.DNI,"12345678"),"Juan","Perez");
    colaborador.addContacto(new Contacto("jperez@gmail.com", new Email(enviadorDeMails)));
    Mensaje mensaje = new Mensaje("Hola juan", "Notificacion");
    colaborador.getContactos().get(0).notificar(mensaje);

    greenMail.waitForIncomingEmail(5000,1);
    Message[] messages = greenMail.getReceivedMessages();

    Assertions.assertEquals(1, messages.length);
    Assertions.assertEquals(mensaje.getAsunto(), messages[0].getSubject());
    Assertions.assertEquals(mensaje.getMensaje(), messages[0].getContent());
  }

/*

    @Test
    public void enviarMailPosta() {
        enviadorDeMails = EnviadorDeMails.getInstancia(new ConfigReader(),"gmail");
        enviadorDeMails.enviar("jmprividera@gmail.com", new Mensaje("hola","hola"));
    }

*/
}
