package ar.edu.utn.frba.dds.simeal.models.entities.personas.medioContacto;

public class Email implements MedioContacto {
  String email;

  public Email(String mail) {
    this.email = mail;
  }

  @Override
  public void notificar(String mensaje) {

  }
}
