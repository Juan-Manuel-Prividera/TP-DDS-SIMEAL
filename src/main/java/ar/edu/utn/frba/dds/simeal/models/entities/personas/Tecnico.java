package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.AreaDeCobertura;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.ReceptorDeNotificaciones;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

  @Embedded
  private Documento documento;

  @Column(name = "cuil")
  private String cuil;

  @OneToMany
  @JoinColumn(name = "tecnico_id")
  private List<Contacto> contactos;

  @OneToOne
  @JoinColumn(name = "contactoPreferido", referencedColumnName = "id")
  private Contacto contactoPreferido;

  @Embedded
  private AreaDeCobertura areaDeCobertura;

  public Tecnico(Contacto contacto, AreaDeCobertura areaDeCobertura) {
    this.contactoPreferido = contacto;
    this.areaDeCobertura = areaDeCobertura;
  }
  public void recibirNotificacion(Mensaje mensaje) {
    contactoPreferido.notificar(mensaje);
  }



}

