package ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TarjetaPersonaVulnerable {
  private final String codigo;
  private final PersonaVulnerable personaVulnerable;
  private final int limiteDeUsoDiario = 4;
  private final int retirosAdicionalesPorMenores = 1;
  // Cuando se crea un Retiro se debe sumar uno a este atributo, en Controller
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

  public void resetearUsos() {
    usosHechos = 0;
  }

}
