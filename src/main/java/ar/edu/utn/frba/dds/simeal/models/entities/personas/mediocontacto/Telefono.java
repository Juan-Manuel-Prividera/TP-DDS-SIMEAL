package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.enviadormails.Enviador;

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
