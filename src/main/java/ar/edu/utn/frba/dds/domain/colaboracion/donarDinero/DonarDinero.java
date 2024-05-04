package ar.edu.utn.frba.dds.domain.colaboracion.donarDinero;

import ar.edu.utn.frba.dds.domain.colaboracion.Colaboracion;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class DonarDinero implements Colaboracion {
  private Frecuencia frecuencia;
  private LocalDate fecha = LocalDate.now();
  private final Integer cantidadDinero;
  private Float factorDeReconocimiento = 0.5F;

  @Override
  public void colaborar(){
  }

  @Override
  public Float calcularReconocimientoParcial(){
    return cantidadDinero.floatValue() * this.factorDeReconocimiento;
  }
}
