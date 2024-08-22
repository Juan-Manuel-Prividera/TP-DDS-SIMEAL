package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.enviadores.EnviadorDeMails;

public class Email implements MedioContacto {
  private EnviadorDeMails enviador;


  public Email(EnviadorDeMails enviador) {
    this.enviador = enviador;
  }

  @Override
  public void notificar(String destinatario, Mensaje mensaje) {
    enviador.enviar(destinatario, mensaje);
  }

}
