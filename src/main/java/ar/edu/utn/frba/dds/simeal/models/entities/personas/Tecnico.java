package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.AreaDeCobertura;
import ar.edu.utn.frba.dds.simeal.models.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.ReceptorDeNotificaciones;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.jboss.jandex.TypeTarget;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tecnico")
public class Tecnico extends Persistente implements ReceptorDeNotificaciones {
  @Column(name = "nombre")
  private String nombre;

  @Column(name = "apellido")
  private String apellido;

  @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  @Embedded
  private Documento documento;

  @Column(name = "cuil")
  private String cuil;

  @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  @OneToMany
  @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
  private List<Contacto> contactos;

  @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  @OneToOne
  @JoinColumn(name = "contacto_preferido_id", referencedColumnName = "id")
  private Contacto contactoPreferido;

  @Embedded @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  private AreaDeCobertura areaDeCobertura;

  @OneToOne
  @Cascade({org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.MERGE})
  @JoinColumn(name = "usuario_id", referencedColumnName = "id")
  private Usuario usuario;

  public Tecnico(Contacto contacto, AreaDeCobertura areaDeCobertura) {
    this.contactoPreferido = contacto;
    this.areaDeCobertura = areaDeCobertura;
  }
  public void recibirNotificacion(Mensaje mensaje) {
    contactoPreferido.notificar(mensaje);
  }



}

