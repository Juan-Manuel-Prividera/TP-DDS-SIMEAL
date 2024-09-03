package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tarjetaColaborador")
public class TarjetaColaborador extends Persistente {

  @OneToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador colaborador;

  @Column(name = "fechaEntrega")
  private LocalDate fechaEntrega;
  @Column(name = "tarjetaActiva")
  private Boolean activa;

}
