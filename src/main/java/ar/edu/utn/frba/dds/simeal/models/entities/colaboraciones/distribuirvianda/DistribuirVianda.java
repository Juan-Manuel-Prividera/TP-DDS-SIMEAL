package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda;


import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.service.ConfigReader;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
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
                                        LocalDate fechaDeRealizacion, int cantidadViandasMover) {
    DistribuirVianda distribuirVianda = DistribuirVianda.builder()
        .colaborador(colaborador)
        .fechaDeRealizacion(fechaDeRealizacion)
        .cantidadViandasMover(cantidadViandasMover)
        .build();
    distribuirVianda.getColaborador()
        .sumarPuntosReconocimiento(distribuirVianda.calcularReconocimientoParcial());
    return distribuirVianda;
  }

  @Override
  public double calcularReconocimientoParcial() {
    return this.cantidadViandasMover * factorDeReconocimiento;
  }

}
