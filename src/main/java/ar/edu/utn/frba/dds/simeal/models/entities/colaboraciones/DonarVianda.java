package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.Vianda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;


@Builder
@AllArgsConstructor
public class DonarVianda implements ColaboracionPuntuable {
  @Getter
  private final Colaborador colaborador;
  private final LocalDate fechaDeRealizacion;
  private Vianda vianda;
  @Builder.Default
  private final double factorDeReconocimiento = 1.5;


  public static DonarVianda create(Colaborador colaborador, LocalDate fechaDeRealizacion) {
    DonarVianda donarVianda = DonarVianda.builder()
        .colaborador(colaborador)
        .fechaDeRealizacion(fechaDeRealizacion)
        .build();

    donarVianda.getColaborador()
        .sumarPuntosReconocimiento(donarVianda.calcularReconocimientoParcial());
    return donarVianda;
  }

  @Override
  public double calcularReconocimientoParcial() {
    return this.factorDeReconocimiento;
  }
}
