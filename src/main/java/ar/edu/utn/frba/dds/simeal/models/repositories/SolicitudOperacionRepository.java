package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.SolicitudOperacionHeladera;

import java.util.ArrayList;
import java.util.List;

public class SolicitudOperacionRepository extends Repositorio {
  public List<SolicitudOperacionHeladera> getPorTarjetaColaborador(Long id) {
    List<SolicitudOperacionHeladera> solicitudOperacionHeladera = new ArrayList<>();
    beginTransaction();
    solicitudOperacionHeladera = entityManager()
      .createQuery(" FROM " + SolicitudOperacionHeladera.class.getName() + " WHERE tarjeta_colaborador_id= :id AND hora_solicitud >= CURRENT_DATE ", SolicitudOperacionHeladera.class)
      .setParameter("id", id).getResultList();
    commitTransaction();
    return solicitudOperacionHeladera;
  }
}
