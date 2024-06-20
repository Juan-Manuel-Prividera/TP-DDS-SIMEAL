package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Tarjeta;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;



@Builder
@AllArgsConstructor
public class DarDeAltaPersonaVulnerable implements ColaboracionPuntuable {
  @Getter
  private final Colaborador colaborador;
  private final LocalDate fechaDeRealizacion;
  private final PersonaVulnerable personaVulnerable;
  private final Tarjeta tarjeta;
  @Builder.Default
  private double factorDeReconocimiento = 2;

  public static DarDeAltaPersonaVulnerable create(Colaborador colaborador,
                                                  LocalDate fechaDeRealizacion) {
    DarDeAltaPersonaVulnerable darDeAltaPersonaVulnerable = DarDeAltaPersonaVulnerable.builder()
        .colaborador(colaborador)
        .fechaDeRealizacion(fechaDeRealizacion)
        .build();
    darDeAltaPersonaVulnerable.getColaborador()
        .sumarPuntosReconocimiento(darDeAltaPersonaVulnerable.calcularReconocimientoParcial());

    return darDeAltaPersonaVulnerable;
  }

  @Override
  public double calcularReconocimientoParcial() {
    return this.factorDeReconocimiento;
  }
}
