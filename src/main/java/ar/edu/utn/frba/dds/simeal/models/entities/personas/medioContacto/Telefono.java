package ar.edu.utn.frba.dds.simeal.models.entities.personas.medioContacto;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.enviadorMails.Enviador;

public class Telefono implements MedioContacto {
  String numero;
  Enviador enviador;

  public Telefono(String numero, Enviador enviador) {
    this.numero = numero;
    this.enviador = enviador;
  }

  @Override
  public void notificar(Mensaje mensaje) {
    enviador.enviar(numero, mensaje);
  }
}
