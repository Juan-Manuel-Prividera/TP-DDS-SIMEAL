package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirVianda;


import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class DistribuirVianda implements Colaboracion {
  private Colaborador colaborador;
  private LocalDate fechaDeRealizacion;
  private Heladera origen;
  private Heladera destino;
  private Motivo motivo;
  private int cantidadViandasMover;
  @Builder.Default
  private double factorDeReconocimiento = 1;

  @Override
  public double calcularReconocimientoParcial() {
    return this.cantidadViandasMover * factorDeReconocimiento;
  }

}
