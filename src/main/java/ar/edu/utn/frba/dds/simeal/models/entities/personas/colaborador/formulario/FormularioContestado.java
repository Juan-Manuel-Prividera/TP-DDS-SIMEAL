package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "formulario_contestado")
@NoArgsConstructor
public class FormularioContestado extends Persistente {
  @OneToMany
  @JoinColumn(name="formulario_contestado_id", referencedColumnName = "id")
  private List<Respuesta> respuestas;
  @Column(name="fecha_de_respuesta")
  private LocalDate fechaRespuesta;


  public FormularioContestado(List<Respuesta> respuestas) {
    this.respuestas = respuestas;
  }
}
