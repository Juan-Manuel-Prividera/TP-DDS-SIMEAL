package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;

public interface MedioContacto {

  void notificar(String destinatario, Mensaje mensaje);
}

