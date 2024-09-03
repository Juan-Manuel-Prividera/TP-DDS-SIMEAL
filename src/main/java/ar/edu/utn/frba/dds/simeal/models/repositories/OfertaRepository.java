package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;

import java.util.ArrayList;
import java.util.List;

public class OfertaRepository extends Repositorio{
  public List<Oferta> getPorColaborador(Long id) {
    List<Oferta> colaboraciones = new ArrayList<>();
    beginTransaction();
    colaboraciones = entityManager()
      .createQuery(" FROM" + Oferta.class.getName() + " WHERE colaborador_id= :id", Oferta.class)
      .setParameter("id", id)
      .getResultList();
    commitTransaction();
    return colaboraciones;
  }}

