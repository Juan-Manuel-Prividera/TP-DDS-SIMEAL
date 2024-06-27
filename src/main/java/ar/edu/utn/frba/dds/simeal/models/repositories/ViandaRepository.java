package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class ViandaRepository {
  @Getter
  private List<Vianda> viandas;

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
    // TODO
  }
  public void eliminar(Vianda vianda) {
    //TODO
  }
  public void actualizar(Vianda vianda) {
    //TODO
  }
}
