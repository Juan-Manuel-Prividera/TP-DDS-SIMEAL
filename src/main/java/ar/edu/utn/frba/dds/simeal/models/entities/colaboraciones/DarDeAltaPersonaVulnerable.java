package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.TarjetaPersonaVulnerable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "dar_de_alta_persona_vulnerable")
public class DarDeAltaPersonaVulnerable extends Persistente implements ColaboracionPuntuable {
  @ManyToOne
  @Cascade({org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.MERGE})
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador colaborador;

  @Column
  private LocalDate fechaDeRealizacion;

  @OneToOne
  @JoinColumn(name = "persona_vulnerable_id", referencedColumnName = "id")
  private PersonaVulnerable personaVulnerable;

  @OneToOne
  @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
  private TarjetaPersonaVulnerable tarjeta;

  @Builder.Default
  @Transient
  private double factorDeReconocimiento = 2;

 public DarDeAltaPersonaVulnerable(Colaborador colaborador, PersonaVulnerable personaVulnerable, TarjetaPersonaVulnerable tarjeta) {
   this.colaborador = colaborador;
   this.personaVulnerable = personaVulnerable;
   this.tarjeta = tarjeta;
   this.fechaDeRealizacion = LocalDate.now();
 }

  public static DarDeAltaPersonaVulnerable create(Colaborador colaborador,
                                                  LocalDate fechaDeRealizacion) {
    DarDeAltaPersonaVulnerable darDeAltaPersonaVulnerable = DarDeAltaPersonaVulnerable.builder()
        .colaborador(colaborador)
        .fechaDeRealizacion(fechaDeRealizacion)
        .build();
    darDeAltaPersonaVulnerable.getColaborador()
        .sumarPuntosReconocimiento(darDeAltaPersonaVulnerable.calcularReconocimientoParcial());

    return darDeAltaPersonaVulnerable;
  }

  @Override
  public double calcularReconocimientoParcial() {
    return this.factorDeReconocimiento;
  }
}
