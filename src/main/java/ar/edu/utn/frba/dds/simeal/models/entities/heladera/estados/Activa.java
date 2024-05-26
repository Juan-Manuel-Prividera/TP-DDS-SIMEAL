package ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados;


public class Activa implements EstadoHeladera {
  private String notificacion = "La heladera se encuentra activa: ";

  public Activa() {
  }

  @Override
  public String notificarEstado(String mensaje) {
    return notificacion + mensaje;
  }

  @Override
  public boolean validarEstado() {
    return true;
  }

}
