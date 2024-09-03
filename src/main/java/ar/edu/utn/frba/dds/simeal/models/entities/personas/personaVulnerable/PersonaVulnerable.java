package ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@AllArgsConstructor
@Entity
@Table(name = "personaVulnerable")
@NoArgsConstructor(force = true)
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
  private List<PersonaVulnerable> hijos = new ArrayList<>();
  @Embedded
  private Documento documento;
  @ManyToOne
  @JoinColumn(name = "padre_id", referencedColumnName = "id")
  private PersonaVulnerable padre;

  public PersonaVulnerable(LocalDate of) {
    this.fechaNacimiento = of;
  }


  public boolean esMenor() {
    return LocalDate.now().getYear() - this.fechaNacimiento.getYear() < 18;
  }

  public int cantHijosMenores() {
    return this.hijos.stream().filter(PersonaVulnerable::esMenor).toList().size();
  }
}
