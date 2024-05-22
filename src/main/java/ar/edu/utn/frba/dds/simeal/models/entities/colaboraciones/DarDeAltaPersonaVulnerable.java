package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Tarjeta;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class DarDeAltaPersonaVulnerable implements Colaboracion{

  private Colaborador colaborador;
  private LocalDate fechaDeRealizacion;
  private PersonaVulnerable personaVulnerable;
  private Float falctorDeReconocimiento = 2F;
  private Tarjeta tarjeta;
/*
  public DarDeAltaPersonaVulnerable(Colaborador colaborador, LocalDate fechaDeRealizacion, Tarjeta tarjeta) {
    this.colaborador = colaborador;
    this.fechaDeRealizacion = fechaDeRealizacion;
    this.tarjeta = tarjeta;
  }
*/

  @Override
  public Float calcularReconocimientoParcial(){
    return this.falctorDeReconocimiento;
  }

}
