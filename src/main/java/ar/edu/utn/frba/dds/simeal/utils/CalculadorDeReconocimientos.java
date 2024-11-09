package ar.edu.utn.frba.dds.simeal.utils;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;

import java.util.List;


public class CalculadorDeReconocimientos {
  public static double calcularReconocimientoTotal(Colaborador colaborador) {
    double reconocimiento =  colaborador.getPuntosDeReconocimientoParcial();

    List<AdherirHeladera> adherirHeladeras = (List<AdherirHeladera>) ((ColaboracionRepository) ServiceLocator
        .getRepository(ColaboracionRepository.class))
        .getPorColaborador(colaborador.getId(), AdherirHeladera.class);

    if (adherirHeladeras == null || adherirHeladeras.isEmpty()) {
      return reconocimiento;
    }

    for (AdherirHeladera adherirHeladera : adherirHeladeras) {
      reconocimiento += adherirHeladera.calcularReconocimientoParcial();
    }
    return reconocimiento;
  }
}
