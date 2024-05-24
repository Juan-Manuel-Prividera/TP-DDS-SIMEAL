package ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados;


public class Activa implements EstadoHeladera {
  private String notificacion = "La heladera se encuentra activa: ";

  public Activa(String notificacion) {
    this.notificacion += notificacion;
  }

  @Override
  public String notificarEstado() {
    return notificacion;
  }

  @Override
  public boolean validarEstado() {
    return true;
  }

}
