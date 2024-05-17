package ar.edu.utn.frba.dds.domain.colaboracion;

import ar.edu.utn.frba.dds.domain.personas.Colaborador;
import ar.edu.utn.frba.dds.domain.personas.documentacion.Tarjeta;
import ar.edu.utn.frba.dds.domain.personas.PersonaVulnerable;
import jdk.jfr.BooleanFlag;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
  public DarDeAltaPersonaVulnerable(Colaborador colaborador, LocalDate fechaDeRealizacion) {
    this.colaborador = colaborador;
    this.fechaDeRealizacion = fechaDeRealizacion;
  }
*/

  @Override
  public void colaborar(){
    //Acá no hace falta hacer nada? Porque la persona ya tiene que estar creada de antemano
  }
  @Override
  public Float calcularReconocimientoParcial(){
    return this.falctorDeReconocimiento;
  }

}
