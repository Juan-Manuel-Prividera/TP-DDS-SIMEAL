package ar.edu.utn.frba.dds.domain.colaboracion.donarDinero;

import ar.edu.utn.frba.dds.domain.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.domain.personas.Colaborador;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class DonarDinero implements Colaboracion {
  private Colaborador colaborador;
  private Frecuencia frecuencia;
  private LocalDate fechaDeRealizacion;
  private Integer cantidadDinero;
  private Float factorDeReconocimiento = 0.5F;

/*
  public DonarDinero(Colaborador colaborador, LocalDate fechaDeRealizacion) {
    this.colaborador = colaborador;
    this.fechaDeRealizacion = fechaDeRealizacion;
  }
*/
  @Override
  public void colaborar(){

  }

  @Override
  public Float calcularReconocimientoParcial(){
    return cantidadDinero.floatValue() * this.factorDeReconocimiento;
  }
}
