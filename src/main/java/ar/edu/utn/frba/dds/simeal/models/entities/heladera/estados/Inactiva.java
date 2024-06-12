package ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados;


public class Inactiva implements EstadoHeladera {
  private String notificacion = "La heladera se encuentra inactiva: ";

  public Inactiva() {
  }

  @Override
  public String notificarEstado(String mensaje) {
    return notificacion + mensaje;
  }

  @Override
  public boolean disponible() {
    return false;
  }
}
