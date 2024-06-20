package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.TipoEvento;

import java.util.ArrayList;
import java.util.List;

public class SuscripcionesRepository {
  private List<Suscripcion> suscripciones = new ArrayList<>();

  public Suscripcion buscarPor(Heladera heladera, TipoEvento tipoEvento) {
    return suscripciones.stream()
        .filter(s -> s.getHeladera().getUbicacion().equals(heladera.getUbicacion()) && s.interesaEsteEvento(tipoEvento))
        .toList()
        .get(0);
  }

  public void guardar(Suscripcion suscripcion) {
    suscripciones.add(suscripcion);
  }
  public void eliminar(Suscripcion suscripcion) {
    suscripciones.remove(suscripcion);
  }
}
