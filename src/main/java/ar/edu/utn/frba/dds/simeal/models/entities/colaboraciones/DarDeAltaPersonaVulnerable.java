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
  @Builder.Default
  private double falctorDeReconocimiento = 2;
  private Tarjeta tarjeta;

//  public DarDeAltaPersonaVulnerable(){
//    fechaDeRealizacion = LocalDate.now();
//  }

  @Override
  public double calcularReconocimientoParcial(){
    return this.falctorDeReconocimiento;}
}
