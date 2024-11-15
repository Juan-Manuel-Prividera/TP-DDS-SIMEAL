package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;


import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;

import java.time.LocalDate;

public interface ColaboracionPuntuable {
  double calcularReconocimientoParcial();
  LocalDate getFechaDeRealizacion();
  Colaborador getColaborador();
  String getCantidad();
}
