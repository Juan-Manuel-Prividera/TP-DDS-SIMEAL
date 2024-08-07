package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.Getter;

@Getter
public class QuedanPocasViandas implements Notificacion {
  private Mensaje mensaje;

  public QuedanPocasViandas(Heladera heladera) {
    mensaje = new Mensaje("Quedan pocas viandas en la heladera: "
        + heladera.getNombre());
  }

  @Override
  public Boolean interesaEsteEvento(TipoEvento tipoEvento, Suscripcion suscripcion, int cantidadViandas) {
    return tipoEvento.equals(TipoEvento.RETIRO) && suscripcion.getCantidadViandasCriticas() < cantidadViandas;
  }
}
