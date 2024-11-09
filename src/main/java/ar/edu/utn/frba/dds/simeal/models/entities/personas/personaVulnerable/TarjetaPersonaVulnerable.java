package ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor(force = true)
@Entity
@Table(name = "tarjeta_persona_vulnerable")
public class TarjetaPersonaVulnerable extends Persistente {
  @Column(name = "codigo")
  private final String codigo;

  @OneToOne
  @JoinColumn(name = "persona_vulnerable_id", referencedColumnName = "id")
  private final PersonaVulnerable personaVulnerable;

  @Column(name = "usosHechos")
  private Integer usosHechos; // Cuando se crea un Retiro se debe sumar uno a este atributo, en Controller

  @Transient
  private final int limiteDeUsoDiario = 4;
  @Transient
  private final int retirosAdicionalesPorMenores = 1;

  public TarjetaPersonaVulnerable(String codigo, PersonaVulnerable personaVulnerable){
    this.codigo = codigo;
    this.personaVulnerable = personaVulnerable;
    this.usosHechos = 0;
  }
  public int usosRestantes() {
    return this.calcularLimiteDeUso() - usosHechos;
  }

  public boolean puedeRetirar() {
    return usosHechos < calcularLimiteDeUso();
  }

  public int calcularLimiteDeUso() {
    Logger.debug("La persona tiene: " + this.personaVulnerable.cantHijosMenores() + " hijos menores");
    return limiteDeUsoDiario + (this.personaVulnerable.cantHijosMenores() * retirosAdicionalesPorMenores);
  }

  public void resetearUsos() {
    usosHechos = 0;
  }

}
