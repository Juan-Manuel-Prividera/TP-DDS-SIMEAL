package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Canje;

import java.util.ArrayList;
import java.util.List;

public class CanjeRepository extends Repositorio {
  public List<Canje> getPorColaborador(Long Id) {
    List<Canje> canjes = new ArrayList<>();
    beginTransaction();
    canjes = entityManager()
      //Aparece como error de sintaxis, pero funciona. Momento xd
      .createQuery("FROM " + Canje.class.getName() + " WHERE beneficiario_id= :id", Canje.class)
      .setParameter("id", Id)
      .getResultList();
    commitTransaction();
    return canjes;
  }
}
