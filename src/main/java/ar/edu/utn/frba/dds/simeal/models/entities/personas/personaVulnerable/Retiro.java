package ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;


public class Retiro {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;
  @Column
  @Getter
  private LocalDate fecha;

  @ManyToOne
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  private Heladera heladera;
  @ManyToOne
  @JoinColumn(name = "codigo_tarjeta", referencedColumnName = "codigo")
  private TarjetaPersonaVulnerable tarjeta;

  public void Retiro(Heladera heladera, TarjetaPersonaVulnerable tarjeta) {
    this.fecha = LocalDate.now();
    this.heladera = heladera;
    this.tarjeta = tarjeta;
  }
}
