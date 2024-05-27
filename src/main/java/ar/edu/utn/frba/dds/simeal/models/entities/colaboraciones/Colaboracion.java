package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;


import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;


public interface Colaboracion {
  double calcularReconocimientoParcial();

  Colaborador getColaborador();
}
