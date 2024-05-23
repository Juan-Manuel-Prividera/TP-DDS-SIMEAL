package ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados;

public class Inactiva implements EstadoHeladera {
  private String notificacion;


  @Override
  public String notificarEstado() {
    return null;
  }
  public boolean validarEstado(){
    return false;
  }
}
