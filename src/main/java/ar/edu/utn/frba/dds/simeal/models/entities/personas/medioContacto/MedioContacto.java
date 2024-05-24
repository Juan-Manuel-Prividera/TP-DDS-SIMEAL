package ar.edu.utn.frba.dds.simeal.models.entities.personas.medioContacto;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.enviadorMails.Enviador;

public interface MedioContacto {


  public void notificar(Mensaje mensaje);
}

