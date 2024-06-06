package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import java.util.List;


// Es una buena idea hacer esto singleton?
public class CalculadorDeReconocimientos {
  private static CalculadorDeReconocimientos instancia = null;
  private final List<ColaboracionPuntuable> colaboraciones;

  public static CalculadorDeReconocimientos getInstance(List<ColaboracionPuntuable> colaboraciones)
  {
    if (instancia == null || instancia.colaboraciones != colaboraciones) {
      instancia = new CalculadorDeReconocimientos(colaboraciones);
    }

    return instancia;
  }
  private CalculadorDeReconocimientos(List<ColaboracionPuntuable> colaboraciones) {
    this.colaboraciones = colaboraciones;
  }

  public double calcularReconocimientoTotal() {
    double reconocimiento = 0;
    for (ColaboracionPuntuable colaboracionPuntuable : colaboraciones) {
      reconocimiento += colaboracionPuntuable.calcularReconocimientoParcial();
    }
    return reconocimiento;
  }

}
