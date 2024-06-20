package ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados;


public class Inactiva implements EstadoHeladera {

  public Inactiva() {
  }

  @Override
  public boolean disponible() {
    return false;
  }
}
