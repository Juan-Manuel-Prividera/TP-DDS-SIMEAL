package ar.edu.utn.frba.dds.colaboracion.distribuirVianda;

import ar.edu.utn.frba.dds.heladera.Heladera;
import ar.edu.utn.frba.dds.viandas.Vianda;
import ar.edu.utn.frba.dds.colaboracion.Colaboracion;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class DistribuirVianda implements Colaboracion {

  private final Heladera origen, destino;
  private final Motivo motivo;
  private final List<Vianda> viandas;
  private Float factorDeReconocimiento = 1F;
  @Override
  public void colaborar(){
    this.origen.retirarViandas(viandas);
    this.destino.ingresarViandas(viandas);
  }

  @Override
  public Float calcularReconocimientoParcial(){
    return this.factorDeReconocimiento;
  }

  public Integer cantViandasAfectadas(){
    return this.viandas.toArray().length;
  }
}
