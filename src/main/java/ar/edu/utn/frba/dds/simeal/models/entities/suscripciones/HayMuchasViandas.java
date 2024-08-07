package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.Getter;

@Getter
public class HayMuchasViandas implements Notificacion {
  private Mensaje mensaje;

  public HayMuchasViandas(Heladera heladera) {
    mensaje = new Mensaje("Hay muchas viandas en la heladera: "
        + heladera.getNombre());
  }

  @Override
  public Boolean interesaEsteEvento(TipoEvento tipoEvento, Suscripcion suscripcion, int cantidadViandas) {
    return tipoEvento.equals(TipoEvento.INGRESO) && suscripcion.getCantidadViandasCriticas() > cantidadViandas;
  }

}




