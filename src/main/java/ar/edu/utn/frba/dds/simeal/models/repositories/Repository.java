package ar.edu.utn.frba.dds.simeal.models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public interface Repository<T> {
  void guardar(T t);
  void eliminar(Long id);
  void desactivar(Long id);
  void actualizar(T t);
  List<T> obtenerTodos();
}
