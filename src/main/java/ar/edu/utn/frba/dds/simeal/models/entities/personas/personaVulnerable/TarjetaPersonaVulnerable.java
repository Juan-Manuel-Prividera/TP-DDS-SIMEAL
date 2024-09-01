package ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor(force = true)
@Entity
@Table(name = "tarjetaPersonaVulnerable")
public class TarjetaPersonaVulnerable extends Persistente {
  @Column(name = "codigo")
  private final String codigo;
  @OneToOne
  @JoinColumn(name = "persona_vulnerable_id", referencedColumnName = "id")
  private final PersonaVulnerable personaVulnerable;
  private final int limiteDeUsoDiario = 4;
  private final int retirosAdicionalesPorMenores = 1;
  // Cuando se crea un Retiro se debe sumar uno a este atributo, en Controller
  @Column(name = "usosHechos")
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
