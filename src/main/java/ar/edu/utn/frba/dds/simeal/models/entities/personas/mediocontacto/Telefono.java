package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;

public class Telefono implements MedioContacto {
  private String numero;

  public Telefono(String numero) {
    this.numero = numero;
  }

  @Override
  public void notificar(Mensaje mensaje) {
  }
}
