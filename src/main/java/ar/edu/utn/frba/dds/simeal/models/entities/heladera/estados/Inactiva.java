package ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados;


public class Inactiva implements EstadoHeladera {
  private String notificacion = "La heladera se encuentra inactiva: ";

  public Inactiva(String notificacion) {
    this.notificacion += notificacion;
  }

  @Override
  public String notificarEstado() {
    return notificacion;
  }

  @Override
  public boolean validarEstado() {
    return false;
  }
}
