package ar.edu.utn.frba.dds.simeal.models.usuario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="permisos")
public class Permiso extends Persistente {
  @Column(name="endpoint")
  @Convert(converter = PatternConverter.class)
  private Pattern endpoint;

  @Column(name="verbo")
  @Enumerated(EnumType.STRING)
  private TipoMetodoHttp verbo;

  public Permiso(String ep, TipoMetodoHttp met){
    String trimmedEp = ep.replaceAll("^/|/$", "");
    endpoint = Pattern.compile("/?" + trimmedEp + "/?");
    verbo = met;
  }

  public boolean isAllowed(String endpoint, TipoMetodoHttp method) {
    return this.endpoint.matcher(endpoint).matches() && this.verbo.equals(method);
  }


}
