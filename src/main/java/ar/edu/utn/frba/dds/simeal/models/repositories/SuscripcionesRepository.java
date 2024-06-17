package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.Suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import java.util.List;

public class SuscripcionesRepository {
  private List<Suscripcion> suscripciones;

  public List<Suscripcion> buscarPor(Heladera heladera) {
    return suscripciones.stream()
        .filter(s -> s.getHeladera().getUbicacion().equals(heladera.getUbicacion()))
        .toList();
  }

  public void guardar(Suscripcion suscripcion) {
    // TODO
  }
  public void eliminar(Suscripcion suscripcion) {
    //TODO
  }
  public void actualizar(Suscripcion suscripcion) {
    //TODO
  }
}
