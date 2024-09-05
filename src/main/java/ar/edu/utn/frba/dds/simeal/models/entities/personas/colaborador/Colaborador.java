package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Rubro;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.FormularioContestado;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.service.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.ReceptorDeNotificaciones;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "colaborador")
public class Colaborador extends Persistente implements ReceptorDeNotificaciones {
  @Column
  private String nombre;

  @Column
  private String apellido;

  @Embedded
  private Documento documento;

  @OneToOne
  @JoinColumn(name="ubicacion_id", referencedColumnName = "id")
  private Ubicacion ubicacion;

  @Column
  private String razonSocial;

  @ManyToOne
  @JoinColumn(name="rubro_id", referencedColumnName = "id")
  private Rubro rubro;

  @Enumerated(EnumType.STRING)
  private TipoJuridico tipoJuridico;

  @OneToOne
  @JoinColumn(name = "formulario_contestado_id", referencedColumnName = "id")
  private FormularioContestado formularioContestado;

  @OneToMany
  @JoinColumn(referencedColumnName = "colaborador_id")
  private final List<Contacto> contactos = new ArrayList<>();

  @OneToOne
  @JoinColumn(name = "contacto_preferido_id", referencedColumnName = "id")
  private Contacto contactoPreferido;

  @ElementCollection(targetClass = TipoColaboracion.class)
  @CollectionTable(name = "colaborador_tipo_colaboracion", joinColumns = @JoinColumn(name = "colaborador_id"))
  @Enumerated(EnumType.STRING)
  private final List<TipoColaboracion> formasDeColaborar = new ArrayList<>();

  @Setter
  @Transient
  private Usuario usuario;

  @Setter
  @Transient
  private double puntosDeReconocimientoParcial;

  public Colaborador(Documento documento, String nombre, String apellido) {
    this.documento = documento;
    this.nombre = nombre;
    this.apellido = apellido;
  }

  public Colaborador(Ubicacion ubicacion) {
    this.ubicacion = ubicacion;
  }

  public void addContacto(Contacto contacto) {
    contactos.add(contacto);
  }

  public void gastarPuntos(double puntos) {
    this.puntosDeReconocimientoParcial -= puntos;
  }

  public void sumarPuntosReconocimiento(double puntosDeReconocimiento) {
    this.puntosDeReconocimientoParcial += puntosDeReconocimiento;
  }

  // Le tienen que llegar los puntos que le aporta las heladeras, cuando tengamos persistencia
  public boolean puedeCanjear(Oferta oferta, double puntosPorHeladeras) {
    return this.puntosDeReconocimientoParcial + puntosPorHeladeras >= oferta.getPuntosNecesarios();
  }

  @Override
  public void recibirNotificacion(Mensaje mensaje) {
    contactoPreferido.notificar(mensaje);
  }

  @Override
  public boolean equals(Object colaborador) {
    if (this == colaborador)
      return true;

    Colaborador colaborador2 = (Colaborador) colaborador;
    if (Objects.equals(colaborador2.getDocumento().getNroDocumento(), this.documento.getNroDocumento()) &&
      colaborador2.getDocumento().getTipoDocumento() == this.documento.getTipoDocumento())
      return true;

    return false;
  }

}

