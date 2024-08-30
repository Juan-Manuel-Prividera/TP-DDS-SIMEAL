package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class SuscripcionesRepository implements Repository<Suscripcion> {
 @Setter
  private List<Suscripcion> suscripciones;

  public SuscripcionesRepository() {
    suscripciones = new ArrayList<>();
  }

  public List<Suscripcion> buscarPor(Heladera heladera) {
    return suscripciones.stream()
        .filter(s -> s.getHeladera().getUbicacion().equals(heladera.getUbicacion()))
        .toList();
  }

  public void guardar(Suscripcion suscripcion) {
    suscripciones.add(suscripcion);
  }

 @Override
 public void eliminar(Long id) {

 }

 @Override
 public void actualizar(Suscripcion suscripcion) {

 }

 @Override
 public List<Suscripcion> obtenerTodos() {
  return List.of();
 }

 public void eliminar(Suscripcion suscripcion) {
    suscripciones.remove(suscripcion);
  }


}
