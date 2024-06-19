package ar.edu.utn.frba.dds.simeal.models.entities.suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;

public interface Suscriptor {
  void recibirNotificacion(Mensaje mensaje);
  int getCantidadCritica();

  Ubicacion getUbicacion();
}
