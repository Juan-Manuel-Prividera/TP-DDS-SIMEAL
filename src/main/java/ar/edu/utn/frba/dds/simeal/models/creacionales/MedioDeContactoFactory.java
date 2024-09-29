package ar.edu.utn.frba.dds.simeal.models.creacionales;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Email;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.MedioContacto;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.EnviadorDeMails;

public class MedioDeContactoFactory {
  public static MedioContacto crearMedioDeContacto(String contacto) {
    if (isAnEmail(contacto)) {
      return new Email(ServiceLocator.getService(EnviadorDeMails.class));
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

}
