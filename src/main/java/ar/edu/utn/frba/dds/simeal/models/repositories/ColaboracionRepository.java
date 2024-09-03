package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ColaboracionRepository extends Repositorio {
  public List<ColaboracionPuntuable> getPorColaborador(Long id, Class<ColaboracionPuntuable> clase) {
    List<ColaboracionPuntuable> colaboraciones = new ArrayList<>();
    beginTransaction();
    colaboraciones = entityManager()
      .createQuery(" FROM" + clase.getName() + " WHERE colaborador_id= :id", clase)
      .setParameter("id", id)
      .getResultList();
    commitTransaction();
    return colaboraciones;
  }
}

