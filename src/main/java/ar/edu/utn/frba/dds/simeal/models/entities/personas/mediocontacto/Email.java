package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.enviadormails.Enviador;

public class Email implements MedioContacto {
  String email;
  Enviador enviador;


  public Email(String mail, Enviador enviador) {
    this.email = mail;
    this.enviador = enviador;
  }

  @Override
  public void notificar(Mensaje mensaje) {
    enviador.enviar(email, mensaje);
  }

}
