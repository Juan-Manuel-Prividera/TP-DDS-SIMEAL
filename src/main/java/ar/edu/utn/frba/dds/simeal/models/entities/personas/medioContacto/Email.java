package ar.edu.utn.frba.dds.simeal.models.entities.personas.medioContacto;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.enviadorMails.Enviador;

public class Email implements MedioContacto {
  String email;
  Enviador enviador;


  public Email(String mail, Enviador enviador) {
    this.email = mail;
    this.enviador = enviador;
  }

  @Override
  public void notificar(Mensaje mensaje) {
    enviador.enviar(email,mensaje);
  }

}
