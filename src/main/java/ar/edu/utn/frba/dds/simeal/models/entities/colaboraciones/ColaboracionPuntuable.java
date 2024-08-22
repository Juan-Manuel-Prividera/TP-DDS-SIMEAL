package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;


import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;


public interface ColaboracionPuntuable extends Colaboracion {
  double calcularReconocimientoParcial();

  Colaborador getColaborador();
}
