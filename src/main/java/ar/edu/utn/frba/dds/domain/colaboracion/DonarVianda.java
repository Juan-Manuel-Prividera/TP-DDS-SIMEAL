package ar.edu.utn.frba.dds.domain.colaboracion;

import ar.edu.utn.frba.dds.domain.personas.Colaborador;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
public class DonarVianda implements Colaboracion{
  private Colaborador colaborador;
  private LocalDate fechaDeRealizacion;
  private Vianda vianda;
  private Float factorDeReconocimiento = 1.5F;

/*
  public DonarVianda(Colaborador colaborador, LocalDate fechaDeRealizacion) {
    this.colaborador = colaborador;
    this.fechaDeRealizacion = fechaDeRealizacion;
  }
*/
  @Override
  public void colaborar(){

  }

  @Override
  public Float calcularReconocimientoParcial(){
    return this.factorDeReconocimiento;
  }
}
