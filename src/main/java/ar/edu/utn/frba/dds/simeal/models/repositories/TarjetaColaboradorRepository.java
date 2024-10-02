package ar.edu.utn.frba.dds.simeal.models.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;

public class TarjetaColaboradorRepository extends Repositorio{
  public TarjetaColaborador getPorColaborador(Long id) {
    TarjetaColaborador tarjetaColaborador;
    beginTransaction();
    tarjetaColaborador = entityManager()
      .createQuery(" FROM " + TarjetaColaborador.class.getName() + " WHERE colaborador_id= :id", TarjetaColaborador.class)
      .setParameter("id", id).getResultList().get(0);
    commitTransaction();
    return tarjetaColaborador;
  }
}
