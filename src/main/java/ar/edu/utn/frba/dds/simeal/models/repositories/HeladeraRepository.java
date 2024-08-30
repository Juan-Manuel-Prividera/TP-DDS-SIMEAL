package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class HeladeraRepository implements Repository<Heladera> {
  @Getter
  private List<Heladera> heladeras;

  public HeladeraRepository() {
    heladeras = new ArrayList<>();
  }

  public void guardar(Heladera heladera) {
    heladeras.add(heladera);
  }

  @Override
  public void eliminar(Long id) {

  }

  @Override
  public void actualizar(Heladera heladera) {

  }

  @Override
  public List<Heladera> obtenerTodos() {
    return List.of();
  }

  public void eliminar(Heladera heladera) {
    heladeras.remove(heladera);
  }

  public List<Heladera> buscarPorCercania(Ubicacion ubicacion, int condicionDeCercania) {
    List<Heladera> heladerasCercanas = new ArrayList<>();
    for (Heladera heladera : heladeras) {
      if(heladera.getUbicacion().estaCercaDe(ubicacion, condicionDeCercania))
        heladerasCercanas.add(heladera);
    }

    return heladerasCercanas;
  }

    public List<Heladera> getHeladerasCercanasA(Ubicacion ubicacion) {
      return heladeras.stream()
              .filter(h -> h.getUbicacion().estaCercaDe(ubicacion, 1000))
              .toList();
    }


}
