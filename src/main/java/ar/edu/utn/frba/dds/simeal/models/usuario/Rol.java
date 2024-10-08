package ar.edu.utn.frba.dds.simeal.models.usuario;


import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="roles")
public class Rol extends Persistente {
  @Column(name="nombre")
  @Enumerated(EnumType.STRING)
  private TipoRol tipo;

  @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  @ManyToMany
  @JoinTable(
          name = "rol_permiso",
          joinColumns = @JoinColumn( name="rol_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "permiso_id", referencedColumnName = "id"))
  private List<Permiso> permisos;

  public Boolean tienePermisoPara(Permiso permiso) {
    return this.permisos.contains(permiso);
  }

}
