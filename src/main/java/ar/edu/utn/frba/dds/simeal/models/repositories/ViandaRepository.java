package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class ViandaRepository implements Repository<Vianda> {
  @Getter
  private List<Vianda> viandas;

  public ViandaRepository() {
    viandas = new ArrayList<>();
  }

  public List<Vianda> buscarPorHeladera(Heladera heladera) {
    List<Vianda> viandasFiltradas = new ArrayList<>();
    for (Vianda vianda : viandas) {
      if (vianda.getHeladera().equals(heladera)) {
        viandasFiltradas.add(vianda);
      }
    }
    return viandasFiltradas;
  }
  public void guardar(Vianda vianda) {
    viandas.add(vianda);
  }

  @Override
  public void eliminar(Long id) {

  }

  public void eliminar(Vianda vianda) {
    viandas.remove(vianda);
  }
  public void actualizar(Vianda vianda) {
    //TODO
  }

  @Override
  public List<Vianda> obtenerTodos() {
    return List.of();
  }


}
