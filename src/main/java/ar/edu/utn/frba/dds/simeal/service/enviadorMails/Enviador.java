package ar.edu.utn.frba.dds.simeal.service.enviadorMails;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;


public interface Enviador {
  void enviar(String destinatario, Mensaje mensaje);
}
