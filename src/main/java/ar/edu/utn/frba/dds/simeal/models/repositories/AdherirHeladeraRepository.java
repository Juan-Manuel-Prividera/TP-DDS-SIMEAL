package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;

import java.util.List;

public class AdherirHeladeraRepository implements Repository<AdherirHeladera>{

  // Deberia recibir el id
  public List<AdherirHeladera> getPorColaborador(Long id) {
    return null;
    // TODO
  }


  @Override
  public void guardar(AdherirHeladera adherirHeladera) {

  }

  @Override
  public void eliminar(Long id) {

  }

  @Override
  public void actualizar(AdherirHeladera adherirHeladera) {

  }

  @Override
  public List<AdherirHeladera> obtenerTodos() {
    return List.of();
  }
}
