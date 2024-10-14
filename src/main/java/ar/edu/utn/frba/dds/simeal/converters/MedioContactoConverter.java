package ar.edu.utn.frba.dds.simeal.converters;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.*;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.EnviadorDeMails;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.telegram.EnviadorTelegram;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.whatsapp.EnviadorDeWpp;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MedioContactoConverter implements AttributeConverter<MedioContacto, String> {
  @Override
  public String convertToDatabaseColumn(MedioContacto medioContacto) {
    if (medioContacto == null) {
      return null; // O lanza una excepción si no quieres permitir nulos
    }
    if (medioContacto instanceof Telefono) {
      return "Telefono";
    } else if (medioContacto instanceof Email) {
      return "Email";
    } else if (medioContacto instanceof Telegram) {
      return "Telegram";
    } else if (medioContacto instanceof WhatsApp) {
      return "WhatsApp";
    } else {
      throw new IllegalArgumentException("Tipo de medio de contacto no reconocido: " + medioContacto);
    }
  }

  @Override
  public MedioContacto convertToEntityAttribute(String s) {
    if ("Telefono".equals(s)) {
      return new Telefono();
    } else if ("Email".equals(s)) {
      return new Email(EnviadorDeMails.getInstancia(new ConfigReader()));
    } else if ("Telegram".equals(s)) {
      return new Telegram(EnviadorTelegram.getInstance());
    } else if ("WhatsApp".equals(s)) {
      return new WhatsApp(EnviadorDeWpp.getInstance());
    } else {
      return null;
    }
  }
}
