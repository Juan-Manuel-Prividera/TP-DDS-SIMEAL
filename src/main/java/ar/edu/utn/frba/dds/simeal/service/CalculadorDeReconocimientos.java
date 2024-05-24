package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import java.util.List;


public class CalculadorDeReconocimientos {

  private List<Colaboracion> colaboraciones;

  public CalculadorDeReconocimientos(List<Colaboracion> colaboraciones) {
    this.colaboraciones = colaboraciones;
  }

  public double calcularReconocimientoTotal(Colaborador colaborador) {
    double reconocimiento = 0;

    for (Colaboracion colaboracion : colaboraciones) {
      reconocimiento +=  colaboracion.calcularReconocimientoParcial();
    }
    return reconocimiento;
  }

}
