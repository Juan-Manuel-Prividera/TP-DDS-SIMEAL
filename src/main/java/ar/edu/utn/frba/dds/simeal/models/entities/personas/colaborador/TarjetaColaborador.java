package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador;


import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "tarjeta_colaborador")
public class TarjetaColaborador extends Persistente {

  @OneToOne @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador colaborador;

  @Column(name = "fechaEntrega")
  private LocalDate fechaEntrega;


}
