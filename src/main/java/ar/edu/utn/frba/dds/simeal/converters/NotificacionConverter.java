package ar.edu.utn.frba.dds.simeal.converters;

import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.HayMuchasViandas;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.HuboUnDesperfecto;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.Notificacion;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.QuedanPocasViandas;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class NotificacionConverter implements AttributeConverter<Notificacion, String> {

  @Override
  public String convertToDatabaseColumn(Notificacion notificacion) {
    return switch(notificacion) {
      case HayMuchasViandas hayMuchasViandas -> "HayMuchasViandas";
      case QuedanPocasViandas quedanPocasViandas -> "QuedanPocasViandas";
      case HuboUnDesperfecto huboUnDesperfecto -> "HuboUnDesperfecto";
      default -> null;
    };
  }

  @Override
  public Notificacion convertToEntityAttribute(String s) {
    return switch (s) {
      case "HayMuchasViandas" -> new HayMuchasViandas();
      case "QuedanPocasViandas" -> new QuedanPocasViandas();
      case "HuboUnDesperfecto" -> new HuboUnDesperfecto();
      default -> null;
    };
  }
}
