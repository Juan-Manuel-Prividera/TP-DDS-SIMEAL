package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DarDeAltaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.donardinero.DonarDinero;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ColaboracionRepository extends Repositorio {
  public List<? extends ColaboracionPuntuable> getPorColaborador(Long id, Class<? extends ColaboracionPuntuable> clase) {
    List<? extends ColaboracionPuntuable> colaboraciones = new ArrayList<>();
    beginTransaction();
    colaboraciones = entityManager()
      .createQuery(" FROM " + clase.getName() + " WHERE colaborador_id= :id", clase)
      .setParameter("id", id)
      .getResultList();
    commitTransaction();
    return colaboraciones;
  }

  public List<ColaboracionPuntuable> getAllPorColaborador(Long id) {
    List<ColaboracionPuntuable> colaboraciones = new ArrayList<>();

    beginTransaction();
    colaboraciones.addAll(entityManager()
      .createQuery(" FROM " + DonarDinero.class.getName() + " WHERE colaborador_id= :id", DonarDinero.class)
      .setParameter("id", id)
      .getResultList());
    colaboraciones.addAll(entityManager()
      .createQuery(" FROM " + DonarVianda.class.getName() + " WHERE colaborador_id= :id", DonarVianda.class)
      .setParameter("id", id)
      .getResultList());
    colaboraciones.addAll(entityManager()
      .createQuery(" FROM " + DistribuirVianda.class.getName() + " WHERE colaborador_id= :id", DistribuirVianda.class)
      .setParameter("id", id)
      .getResultList());
    colaboraciones.addAll(entityManager()
      .createQuery(" FROM " + AdherirHeladera.class.getName() + " WHERE colaborador_id= :id", AdherirHeladera.class)
      .setParameter("id", id)
      .getResultList());
    colaboraciones.addAll(entityManager()
      .createQuery(" FROM " + DarDeAltaPersonaVulnerable.class.getName() + " WHERE colaborador_id= :id", DarDeAltaPersonaVulnerable.class)
      .setParameter("id", id)
      .getResultList());
    commitTransaction();
    return colaboraciones;
  }

  public void guardar(List<ColaboracionPuntuable> colaboraciones) {
    try {
      beginTransaction();
      for (ColaboracionPuntuable colaboracion : colaboraciones) {
        entityManager().persist(colaboracion);
      }
      commitTransaction();
    } catch (Exception e) {
      rollbackTransaction();
    }
  }
}

