package ar.edu.utn.frba.dds.simeal.models.creacionales;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Email;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.MedioContacto;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.WhatsApp;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.EnviadorDeMails;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.whatsapp.EnviadorDeWpp;

public class MedioDeContactoFactory {
  public static MedioContacto crearMedioDeContacto(String contacto) {
    if (isAnEmail(contacto)) {
      return new Email(ServiceLocator.getService(EnviadorDeMails.class));
    } else if (esUnTelefono(contacto)) {
      return new WhatsApp(ServiceLocator.getService(EnviadorDeWpp.class));
    }
    return null;
  }

  private static boolean isAnEmail(String email) {
    for (char c : email.toCharArray()) {
      if ( c == '@')
        return true;
    }
    return false;
  }
  private static boolean esUnTelefono(String telefono) {
    char[] c = telefono.toCharArray();
    return c[0] == '+' && c[1] == '5' && c[2] == '4' && c[3] == '1' && c[4] == '1';
  }

}
