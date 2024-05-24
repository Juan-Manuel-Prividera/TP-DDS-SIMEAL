package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirVianda;

import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
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
  private int cantidadViandasAMover;
  @Builder.Default
  private double factorDeReconocimiento = 1;

  @Override
  public double calcularReconocimientoParcial(){
    return this.cantidadViandasAMover * factorDeReconocimiento;
  }

}
