package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;

import java.util.ArrayList;
import java.util.List;

public class OfertaRepository extends Repositorio{
  public List<Oferta> getPorColaborador(Long Id) {
    List<Oferta> colaboraciones = new ArrayList<>();
    beginTransaction();
    colaboraciones = entityManager()
      //Aparece como error de sintaxis, pero funciona. Momento xd
      .createQuery("FROM " + Oferta.class.getName() + " WHERE colaborador_id= :id", Oferta.class)
      .setParameter("id", Id)
      .getResultList();
    commitTransaction();
    return colaboraciones;
  }}

