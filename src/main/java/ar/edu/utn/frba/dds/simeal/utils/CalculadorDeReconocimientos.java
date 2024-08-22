package ar.edu.utn.frba.dds.simeal.utils;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;

import java.util.List;


public class CalculadorDeReconocimientos {
  public static double calcularReconocimientoTotal(Colaborador colaborador, List<AdherirHeladera> adherirHeladeras) {
    double reconocimiento =  colaborador.getPuntosDeReconocimientoParcial();

    if (adherirHeladeras == null || adherirHeladeras.isEmpty()) {
      return reconocimiento;
    }

    for (AdherirHeladera adherirHeladera : adherirHeladeras) {
      reconocimiento += adherirHeladera.calcularReconocimientoParcial();
    }
    return reconocimiento;
  }

}
