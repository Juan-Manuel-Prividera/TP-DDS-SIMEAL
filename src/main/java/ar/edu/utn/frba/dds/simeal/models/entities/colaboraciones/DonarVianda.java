package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@AllArgsConstructor
public class DonarVianda implements ColaboracionPuntuable {
  @Getter
  private final Colaborador colaborador;
  private final LocalDate fechaDeRealizacion;
  private final Vianda vianda;
  @Builder.Default
  private final double factorDeReconocimiento = 1.5;

  public static DonarVianda create(Colaborador colaborador, LocalDate fechaDeRealizacion) {
    DonarVianda donarVianda = DonarVianda.builder()
        .colaborador(colaborador)
        .fechaDeRealizacion(fechaDeRealizacion)
        .build();
    donarVianda.getColaborador()
        .sumarPuntosReconocimiento(donarVianda.factorDeReconocimiento);
    return donarVianda;
  }
  @Override
  public double calcularReconocimientoParcial() {
    return this.factorDeReconocimiento;
  }
}
