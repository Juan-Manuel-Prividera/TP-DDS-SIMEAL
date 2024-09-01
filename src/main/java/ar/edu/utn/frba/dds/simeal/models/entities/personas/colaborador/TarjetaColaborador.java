package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador;


import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tarjetaColaborador")
public class TarjetaColaborador {
  @Id
  @GeneratedValue
  private Long codigo;

  @OneToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador colaborador;


  @Column(name = "fechaEntrega")
  private LocalDate fechaEntrega;
  @Column(name = "activa")
  private Boolean activa;

}
