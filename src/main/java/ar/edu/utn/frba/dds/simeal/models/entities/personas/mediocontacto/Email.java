package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.EnviadorDeMails;

public class Email implements MedioContacto {
  private String email;
  private EnviadorDeMails enviador;


  public Email(String mail, EnviadorDeMails enviador) {
    this.email = mail;
    this.enviador = enviador;
  }

  @Override
  public void notificar(Mensaje mensaje) {
    enviador.enviar(email, mensaje);
  }

}
