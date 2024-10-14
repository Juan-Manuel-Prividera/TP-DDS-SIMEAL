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
    return switch (medioContacto) {
        case Telefono telefono -> "Telefono";
        case Email email -> "Email";
        case Telegram telegram -> "Telegram";
        case WhatsApp whatsApp -> "WhatsApp";
        default -> null;
      };
  }

  @Override
  public MedioContacto convertToEntityAttribute(String s) {
      return switch (s) {
        case "Telefono" -> new Telefono();
        case "Email" -> new Email(EnviadorDeMails.getInstancia(new ConfigReader()));
        case "Telegram" -> new Telegram(EnviadorTelegram.getInstance());
        case "WhatsApp" -> new WhatsApp(EnviadorDeWpp.getInstance());
        default -> null;
      };
  }
}
