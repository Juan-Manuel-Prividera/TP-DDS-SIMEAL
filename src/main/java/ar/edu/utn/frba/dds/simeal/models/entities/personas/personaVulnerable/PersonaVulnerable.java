package ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@AllArgsConstructor
@Entity
@Table(name = "personaVulnerable")
public class PersonaVulnerable extends Persistente {
  @Column (name = "nombre")
  private String nombre;
  @Column (name = "fechaNacimiento")
  private LocalDate fechaNacimiento;
  @Column (name = "fechaRegistro")
  private LocalDate fechaRegistro;
  @Embedded
  private Ubicacion domilicio;
  @OneToMany(mappedBy = "personaVulnerable")
  private final List<PersonaVulnerable> hijos;
  @Embedded
  private final Documento documento;
  @ManyToOne
  @JoinColumn(name = "padre_id", referencedColumnName = "id")
  private PersonaVulnerable padre;

  public PersonaVulnerable() { }

  public boolean esMenor() {
    return LocalDate.now().getYear() - this.fechaNacimiento.getYear() < 18;
  }

  public int cantHijosMenores() {
    return this.hijos.stream().filter(PersonaVulnerable::esMenor).toList().size();
  }
}
