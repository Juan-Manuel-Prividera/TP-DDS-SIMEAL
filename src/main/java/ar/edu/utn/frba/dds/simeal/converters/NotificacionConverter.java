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
    if (notificacion instanceof HayMuchasViandas) {
      return "HayMuchasViandas";
    } else if (notificacion instanceof QuedanPocasViandas) {
      return "QuedanPocasViandas";
    } else if (notificacion instanceof HuboUnDesperfecto) {
      return "HuboUnDesperfecto";
    } else {
      return null;
    }
  }

  @Override
  public Notificacion convertToEntityAttribute(String s) {
    if ("HayMuchasViandas".equals(s)) {
      return new HayMuchasViandas();
    } else if ("QuedanPocasViandas".equals(s)) {
      return new QuedanPocasViandas();
    } else if ("HuboUnDesperfecto".equals(s)) {
      return new HuboUnDesperfecto();
    } else {
      return null;
    }
  }
}
