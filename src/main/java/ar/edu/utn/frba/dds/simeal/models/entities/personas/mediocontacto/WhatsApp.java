package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.whatsapp.EnviadorDeWpp;

public class WhatsApp implements MedioContacto {
  private EnviadorDeWpp enviador;

  public WhatsApp(EnviadorDeWpp enviador) {
    this.enviador = enviador;
  }

  @Override
  public void notificar(String destinatario, Mensaje mensaje) {
    enviador.enviar(destinatario,mensaje);
  }
}
