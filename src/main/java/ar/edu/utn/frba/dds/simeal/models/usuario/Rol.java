package ar.edu.utn.frba.dds.simeal.models.usuario;


import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import io.javalin.http.HandlerType;
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
  @Column(name="tipo")
  @Enumerated(EnumType.STRING)
  private TipoRol tipo;

  @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  @ManyToMany
  @JoinTable(
          name = "rol_permiso",
          joinColumns = @JoinColumn( name="rol_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "permiso_id", referencedColumnName = "id"))
  private List<Permiso> permisos;

  public Boolean tienePermisoPara(String endpoint, HandlerType metodo) throws Exception {
    if (!metodo.isHttpMethod()) throw new Exception("Metodo pasado no es un http method");

    System.out.println(metodo.toString().toUpperCase());
    TipoMetodoHttp metodoHttp = TipoMetodoHttp.valueOf(metodo.toString().toUpperCase());

    for (Permiso p : this.permisos) {
      if (!p.getActivo()) continue;
      if (p.isAllowed(endpoint, metodoHttp)){
        return true;
      }
    }

    return false;
  }

}
