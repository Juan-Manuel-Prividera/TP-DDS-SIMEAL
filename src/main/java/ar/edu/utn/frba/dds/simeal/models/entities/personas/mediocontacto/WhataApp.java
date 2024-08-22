package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.whatsapp.EnviadorDeWpp;

public class WhataApp implements MedioContacto {
  private EnviadorDeWpp enviador;

  public WhataApp(EnviadorDeWpp enviador) {
    this.enviador = enviador;
  }

  @Override
  public void notificar(String destinatario, Mensaje mensaje) {
    enviador.enviar(destinatario,mensaje);
  }
}
