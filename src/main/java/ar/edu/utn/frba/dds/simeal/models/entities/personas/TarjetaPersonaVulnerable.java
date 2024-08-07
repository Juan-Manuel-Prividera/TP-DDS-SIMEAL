package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import lombok.Getter;


@Getter
public class TarjetaPersonaVulnerable {
  private String codigo;
  private final PersonaVulnerable personaVulnerable;
  private final int limiteDeUsoDiario = 4;
  private final int retirosAdicionalesPorMenores = 1;
  private int usosHechos;

  public TarjetaPersonaVulnerable(String codigo, PersonaVulnerable personaVulnerable){
    this.codigo = codigo;
    this.personaVulnerable = personaVulnerable;
  }

  public boolean puedeRetirar() {
    return usosHechos < calcularLimiteDeUso();
  }

  public int calcularLimiteDeUso() {
    return limiteDeUsoDiario + this.personaVulnerable.cantHijosMenores();
  }

}
