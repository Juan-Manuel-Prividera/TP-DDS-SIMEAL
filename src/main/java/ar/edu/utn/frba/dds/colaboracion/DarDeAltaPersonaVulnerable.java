package ar.edu.utn.frba.dds.colaboracion;

import ar.edu.utn.frba.dds.personas.personaVulnerable.PersonaVulnerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DarDeAltaPersonaVulnerable implements Colaboracion{

  private final PersonaVulnerable personaVulnerable;
  private Float falctorDeReconocimiento = 2F;

  @Override
  public void colaborar(){
    //Acá no hace falta hacer nada? Porque la persona ya tiene que estar creada de antemano
  }
  @Override
  public Float calcularReconocimientoParcial(){
    return this.falctorDeReconocimiento;
  }

}
