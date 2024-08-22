package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.TarjetaPersonaVulnerable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;



@Builder
@AllArgsConstructor
public class DarDeAltaPersonaVulnerable implements ColaboracionPuntuable {
  @Getter
  private final Colaborador colaborador;
  private final LocalDate fechaDeRealizacion;
  private final PersonaVulnerable personaVulnerable;
  private final TarjetaPersonaVulnerable tarjeta;
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
