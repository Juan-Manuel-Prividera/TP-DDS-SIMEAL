package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion;

import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.TipoEvento;

public interface Notificacion {
  Boolean interesaEsteEvento(TipoEvento tipoEvento, Suscripcion suscripcion, int cantidadViandas);
  Mensaje getMensaje();
}
