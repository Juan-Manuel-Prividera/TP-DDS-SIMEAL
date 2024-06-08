package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda;


import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@AllArgsConstructor
public class DistribuirVianda implements ColaboracionPuntuable {
  @Getter
  private final Colaborador colaborador;
  private final LocalDate fechaDeRealizacion;
  private Heladera origen;
  private Heladera destino;
  private Motivo motivo;
  private int cantidadViandasMover;
  @Builder.Default
  private final double factorDeReconocimiento = 1;


  public static DistribuirVianda create(Colaborador colaborador,
                                        int cantidadViandasMover, LocalDate fechaDeRealizacion) {
    DistribuirVianda distribuirVianda = DistribuirVianda.builder()
        .colaborador(colaborador)
        .cantidadViandasMover(cantidadViandasMover)
        .fechaDeRealizacion(fechaDeRealizacion)
        .build();

    distribuirVianda.getColaborador().sumarPuntosReconocimiento(
        cantidadViandasMover * distribuirVianda.factorDeReconocimiento);

    return distribuirVianda;
  }


  @Override
  public double calcularReconocimientoParcial() {
    return this.cantidadViandasMover * factorDeReconocimiento;
  }

}
