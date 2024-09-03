package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import lombok.Getter;

@Getter
public class QuedanPocasViandas implements Notificacion {
  private final Mensaje mensaje;

  public QuedanPocasViandas(Heladera heladera) {
    mensaje = new Mensaje("Quedan pocas viandas en la heladera: "
        + heladera.getNombre());
  }

  @Override
  public Boolean interesaEsteEvento(TipoEvento tipoEvento, Suscripcion suscripcion, int cantidadViandas) {
    return tipoEvento.equals(TipoEvento.RETIRO) && suscripcion.getCantidadViandasCriticas() > cantidadViandas;
  }
}
