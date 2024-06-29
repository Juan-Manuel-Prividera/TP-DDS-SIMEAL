package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;

public interface ReceptorDeNotificaciones {
  void recibirNotificacion(Mensaje mensaje);
}
