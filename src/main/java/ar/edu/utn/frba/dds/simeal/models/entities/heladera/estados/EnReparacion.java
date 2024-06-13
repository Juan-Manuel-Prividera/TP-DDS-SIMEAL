package ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados;

public class EnReparacion implements EstadoHeladera{

  public EnReparacion() {
  }
  @Override
  public boolean disponible() {
    return false;
  }
}
