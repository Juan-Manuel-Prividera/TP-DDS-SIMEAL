package ar.edu.utn.frba.dds.domain.colaboracion.distribuirVianda;

import ar.edu.utn.frba.dds.domain.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.domain.heladera.Heladera;
import ar.edu.utn.frba.dds.domain.personas.Colaborador;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class DistribuirVianda implements Colaboracion {
  private Colaborador colaborador;
  private LocalDate fechaDeRealizacion;
  private Heladera origen, destino;
  private Motivo motivo;
  private List<Vianda> viandas;
  private int cantidadViandasAMover;
  private Float factorDeReconocimiento = 1F;

/*
  public DistribuirVianda(Colaborador colaborador, LocalDate fechaDeRealizacion) {
    this.colaborador = colaborador;
    this.fechaDeRealizacion = fechaDeRealizacion;
  }
*/
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
    return this.viandas.size();
  }
}
