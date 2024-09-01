package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.TarjetaPersonaVulnerable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;



@Builder
@AllArgsConstructor
public class DarDeAltaPersonaVulnerable implements ColaboracionPuntuable {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;
  @Getter
  @ManyToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private final Colaborador colaborador;
  @Column
  private final LocalDate fechaDeRealizacion;
  @OneToOne
  @JoinColumn(name = "persona_vulnerable_id", referencedColumnName = "id")
  private final PersonaVulnerable personaVulnerable;
  @OneToOne
  @JoinColumn(name = "tarjeta_id", referencedColumnName = "id") //Esto capaz cambia
  private final TarjetaPersonaVulnerable tarjeta;
  @Builder.Default
  private double factorDeReconocimiento = 2;

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
