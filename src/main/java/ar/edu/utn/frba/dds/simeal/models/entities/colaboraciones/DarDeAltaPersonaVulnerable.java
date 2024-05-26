package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Tarjeta;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;



@Getter
@Builder
@AllArgsConstructor
public class DarDeAltaPersonaVulnerable implements Colaboracion {
  private Colaborador colaborador;
  private LocalDate fechaDeRealizacion;
  private PersonaVulnerable personaVulnerable;
  @Builder.Default
  private double factorDeReconocimiento = 2;
  private Tarjeta tarjeta;

  public DarDeAltaPersonaVulnerable(Colaborador colaborador, PersonaVulnerable personaVulnerable, Tarjeta tarjeta) {
    this.colaborador = colaborador;
    this.fechaDeRealizacion = LocalDate.now();
    this.personaVulnerable = personaVulnerable;
    this.tarjeta = tarjeta;

    tarjeta.setPersonaVulnerable(personaVulnerable);
  }


  @Override
  public double calcularReconocimientoParcial() {
    return this.factorDeReconocimiento;
  }
}
