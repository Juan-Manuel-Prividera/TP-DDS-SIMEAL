package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.EncargoTecnico;

import java.util.ArrayList;
import java.util.List;

public class EncargoTecnicoRepostiry extends Repositorio {
  public List<EncargoTecnico> getPorTecnico(Long id) {
    List<EncargoTecnico> encargos = new ArrayList<>();
    beginTransaction();
    encargos = entityManager()
      .createQuery(" FROM " + EncargoTecnico.class.getName() + " WHERE tecnico_id = :id", EncargoTecnico.class)
      .setParameter("id", id)
      .getResultList();
    commitTransaction();
    return encargos;
  }
}
