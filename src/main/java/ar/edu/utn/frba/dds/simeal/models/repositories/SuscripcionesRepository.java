package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.TipoEvento;

import java.util.ArrayList;
import java.util.List;

public class SuscripcionesRepository {
  private List<Suscripcion> suscripciones;
  private static SuscripcionesRepository instance;

  public static SuscripcionesRepository getInstance() {
    if(instance == null)
      return new SuscripcionesRepository();
    return instance;
  }

  private SuscripcionesRepository() {
    suscripciones = new ArrayList<>();
  }

  public List<Suscripcion> buscarPor(Heladera heladera, TipoEvento tipoEvento) {
    return suscripciones.stream()
        .filter(s -> s.getHeladera().getUbicacion().equals(heladera.getUbicacion()) && s.interesaEsteEvento(tipoEvento))
        .toList();
  }

  public void guardar(Suscripcion suscripcion) {
    suscripciones.add(suscripcion);
  }
  public void eliminar(Suscripcion suscripcion) {
    suscripciones.remove(suscripcion);
  }
}
