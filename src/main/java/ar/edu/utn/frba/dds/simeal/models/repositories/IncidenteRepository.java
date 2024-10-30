package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;

import java.util.ArrayList;
import java.util.List;

public class IncidenteRepository extends Repositorio{

  public List<Incidente> buscarPorHeladera(Long id) {
    List<Alerta> alertas;
    beginTransaction();
    alertas = entityManager()
      .createQuery("FROM " + Alerta.class.getName() + " WHERE heladera_id =:id")
      .setParameter("id", id)
      .getResultList();

    List<FallaTecnica> fallas;
    fallas = entityManager()
      .createQuery("FROM " + FallaTecnica.class.getName() + " WHERE heladera_id =:id")
      .setParameter("id", id)
      .getResultList();
    commitTransaction();

    List<Incidente> incidentes = new ArrayList<>();
    incidentes.addAll(alertas);
    incidentes.addAll(fallas);
    return incidentes;

  }}
