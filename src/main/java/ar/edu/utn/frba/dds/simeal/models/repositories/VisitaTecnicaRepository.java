package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.VisitaTecnica;

import java.util.ArrayList;
import java.util.List;

public class VisitaTecnicaRepository extends Repositorio {
  public List<VisitaTecnica> getPorTecnico(Long id) {
    List<VisitaTecnica> visitas = new ArrayList<>();
    beginTransaction();
    visitas = entityManager()
      .createQuery(" FROM " + VisitaTecnica.class.getName() + " WHERE tecnico_id = :id", VisitaTecnica.class)
      .setParameter("id", id)
      .getResultList();
    commitTransaction();
    return visitas;
  }
}
