package ar.edu.utn.frba.dds.simeal.models.dtos;

import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.HayMuchasViandas;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.HuboUnDesperfecto;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.QuedanPocasViandas;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuscripcionDTO {
  private Long id;
  private String heladeraNombre;
  private Long heladeraId;
  private String tipoSuscripcion;
  private String cantidadViandas;

  public SuscripcionDTO(Suscripcion suscripcion) {
    this.id = suscripcion.getId();
    this.heladeraNombre = suscripcion.getHeladera().getNombre();
    this.heladeraId = suscripcion.getHeladera().getId();
    this.cantidadViandas = String.valueOf(suscripcion.getCantidadViandasCriticas());

    if (suscripcion.getNotificacion() instanceof HayMuchasViandas) {
      this.tipoSuscripcion = "Hay muchas viandas";
    } else if (suscripcion.getNotificacion() instanceof QuedanPocasViandas) {
      this.tipoSuscripcion = "Quedan pocas viandas";
    } else if (suscripcion.getNotificacion() instanceof HuboUnDesperfecto) {
      this.tipoSuscripcion = "Desperfectos";
    }

  }
}

