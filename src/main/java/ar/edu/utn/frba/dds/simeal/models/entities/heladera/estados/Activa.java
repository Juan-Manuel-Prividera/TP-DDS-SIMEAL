package ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados;


public class Activa implements EstadoHeladera {

  public Activa() {
  }

  @Override
  public boolean disponible() {
    return true;
  }

}
