package ar.edu.utn.frba.dds.simeal.service.enviadorMails;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.medioContacto.Email;

public interface Enviador {
    public void enviar(String destinatario, Mensaje mensaje);
}
