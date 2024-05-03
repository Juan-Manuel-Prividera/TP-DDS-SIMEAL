package ar.edu.utn.frba.dds.colaboracion;

import ar.edu.utn.frba.dds.viandas.Vianda;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DonarVianda implements Colaboracion{

  private final Vianda vianda;
  private Float factorDeReconocimiento = 1.5F;
  @Override
  public void colaborar(){

  }

  @Override
  public Float calcularReconocimientoParcial(){
    return this.factorDeReconocimiento;
  }
}
