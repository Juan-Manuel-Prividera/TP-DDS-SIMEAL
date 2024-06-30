package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.service.enviadores.whatsapp.EnviadorDeWpp;

public class WhataApp implements MedioContacto {
  private String numero;
  private EnviadorDeWpp enviador;
  public WhataApp(String numero, EnviadorDeWpp enviador) {
    this.numero = numero;
    this.enviador = enviador;
  }

  @Override
  public void notificar(Mensaje mensaje) {
    enviador.enviar(numero,mensaje);
  }
}
