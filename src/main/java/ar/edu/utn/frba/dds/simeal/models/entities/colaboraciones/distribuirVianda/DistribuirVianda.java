package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirVianda;

import ar.edu.utn.frba.dds.simeal.models.entities.Vianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import lombok.Builder;
import lombok.Getter;

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

  @Override
  public Float calcularReconocimientoParcial(){
    return cantidadViandasAMover * factorDeReconocimiento;
  }

  public Integer cantViandasAfectadas(){
    return this.viandas.size();
  }
}
