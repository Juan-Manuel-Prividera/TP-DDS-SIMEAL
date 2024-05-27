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
public class DarDeAltaPersonaVulnerable implements Colaboracion {
  @Getter
  private final Colaborador colaborador;
  private final LocalDate fechaDeRealizacion;
  private final PersonaVulnerable personaVulnerable;
  @Builder.Default
  private double factorDeReconocimiento = 2;
  private final Tarjeta tarjeta;

  public DarDeAltaPersonaVulnerable(Colaborador colaborador, PersonaVulnerable personaVulnerable,
                                    Tarjeta tarjeta) {
    this.colaborador = colaborador;
    this.fechaDeRealizacion = LocalDate.now();
    this.personaVulnerable = personaVulnerable;
    this.tarjeta = tarjeta;

  }


  @Override
  public double calcularReconocimientoParcial() {
    return this.factorDeReconocimiento;
  }
}
