package ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera;


import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;
import lombok.Builder;
import lombok.Getter;
import lombok.*;

import java.time.LocalDateTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@Table(name = "solicitudOperacionHeladera")
@AllArgsConstructor
public class SolicitudOperacionHeladera extends Persistente {
  @Enumerated(EnumType.STRING)
  private TipoOperacion tipoOperacion;

  @ManyToOne
  @JoinColumn(name = "codigo_tarjeta_colaborador", referencedColumnName = "id")
  private TarjetaColaborador tarjetaColaborador;

  @ManyToOne
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  private Heladera heladera;

  @Column(name = "cantViandas")
  private Integer cantViandas;
  @Builder.Default
  @Transient
  private int horasParaEjecutarse = 3;
  @Column(name = "horaInicio")
  private LocalDateTime horaInicio;

  public SolicitudOperacionHeladera() { }

  //Por alguna razón, si ejecutas el metodo al mismo tiempo en el que creaste la solicitud, devuelve false
  public boolean puedeEjecutarse(Heladera heladera){
    return heladera == this.heladera && LocalDateTime.now().isBefore(this.horaInicio.plusHours(this.horasParaEjecutarse));
  }
}
