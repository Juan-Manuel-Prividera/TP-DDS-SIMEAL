package ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera;


import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@Table(name = "solicitud_operacion_heladera")
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudOperacionHeladera extends Persistente {
  @Enumerated(EnumType.STRING)
  @Column(name = "tipoOperacion")
  private TipoOperacion tipoOperacion;


  @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  @ManyToOne
  @JoinColumn(name = "tarjeta_colaborador_id", referencedColumnName = "id")
  private TarjetaColaborador tarjetaColaborador;

  @ManyToOne @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  private Heladera heladera;

  @Column(name = "cant_viandas")
  private Integer cantViandas;

  @Builder.Default
  @Transient
  private int horasParaEjecutarse = 3;

  @Column(name = "hora_inicio")
  private LocalDateTime horaInicio;

  @Column(name = "hora_solicitud")
  private LocalDateTime horaSolicitud;


  //Por alguna razón, si ejecutas el metodo al mismo tiempo en el que creaste la solicitud, devuelve false
  public boolean puedeEjecutarse(Heladera heladera){
    return heladera == this.heladera && LocalDateTime.now().isBefore(this.horaInicio.plusHours(this.horasParaEjecutarse));
  }
}
