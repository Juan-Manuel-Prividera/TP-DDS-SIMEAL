package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import java.util.ArrayList;
import java.util.List;

public class SuscripcionesRepository {
  private List<Suscripcion> suscripciones = new ArrayList<>();

  public List<Suscripcion> buscarPor(Heladera heladera, String tipoSuscripcion) {
    return suscripciones.stream()
        .filter(s -> s.getHeladera().getUbicacion().equals(heladera.getUbicacion()) && s.esEsteTipo(tipoSuscripcion))
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
