package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import java.util.ArrayList;
import java.util.List;


public class CalculadorDeReconocimientos {

  private final List<Colaboracion> colaboraciones;

  public CalculadorDeReconocimientos(List<Colaboracion> colaboraciones) {
    this.colaboraciones = new ArrayList<>(colaboraciones);
  }

  public double calcularReconocimientoTotal(Colaborador colaborador) {
    double reconocimiento = 0;
    for (Colaboracion colaboracion : colaboraciones) {
      if (colaboracion.getColaborador().equals(colaborador)) {
        reconocimiento += colaboracion.calcularReconocimientoParcial();
      }
    }
    return reconocimiento;
  }

}
