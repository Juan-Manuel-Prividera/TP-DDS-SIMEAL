package ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados;

public interface EstadoHeladera {
  String notificarEstado(String mensaje);

  boolean disponible();
}
