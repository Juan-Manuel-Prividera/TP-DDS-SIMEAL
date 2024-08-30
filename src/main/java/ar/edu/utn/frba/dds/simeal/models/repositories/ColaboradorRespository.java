package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;

import java.util.List;

public class ColaboradorRespository implements Repository<Colaborador>{
   public List<Colaborador> getAllColaboradores() {
        return null;
        // TODO
    }


  @Override
  public void guardar(Colaborador colaborador) {

  }

  @Override
  public void eliminar(Long id) {

  }

  @Override
  public void actualizar(Colaborador colaborador) {

  }

  @Override
  public List<Colaborador> obtenerTodos() {
    return List.of();
  }
}
