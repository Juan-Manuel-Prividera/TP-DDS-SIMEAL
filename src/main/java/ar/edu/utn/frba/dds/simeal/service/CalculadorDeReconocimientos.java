package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
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
