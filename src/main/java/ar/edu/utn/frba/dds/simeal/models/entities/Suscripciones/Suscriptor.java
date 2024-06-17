package ar.edu.utn.frba.dds.simeal.models.entities.Suscripciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;

public interface Suscriptor {
  void recibirNotificacion(Mensaje mensaje);
  int getCantidadCritica();
}
