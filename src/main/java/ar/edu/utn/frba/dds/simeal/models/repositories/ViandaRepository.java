package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;

import java.util.ArrayList;
import java.util.List;

public class ViandaRepository  extends Repositorio {

  public List<Vianda> buscarPorHeladera(Heladera heladera) {
    List<Vianda> viandas = new ArrayList<>();
    beginTransaction();
    viandas = entityManager().createQuery("FROM " + Vianda.class.getName() + " WHERE heladera_id = :id")
      .setParameter("id", heladera.getId())
      .getResultList();
    commitTransaction();
    return viandas;
  }
}
