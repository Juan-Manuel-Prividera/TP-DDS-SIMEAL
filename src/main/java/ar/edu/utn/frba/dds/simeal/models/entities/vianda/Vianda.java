package ar.edu.utn.frba.dds.simeal.models.entities.vianda;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.creacionales.EventoFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vianda")
public class Vianda extends Persistente {
  @Enumerated(EnumType.STRING)
  private TipoDeComida tipoDeComida;
  @Column
  private LocalDate fechaCaducidad;
  @Column
  private LocalDate fechaDonacion;
  @ManyToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador colaborador;
  @Column
  private int calorias;
  @ManyToOne
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  private Heladera heladera;
  @Column
  private boolean entregada;


  public Vianda(Heladera heladera) {
    this.heladera = heladera;
  }

  public void retirar() {
    setHeladera(null);
    entregada = true;
  }

  // Caso 1: La vianda no estaba en ninguna heladera y se ingresa a una
  // Caso 2: La vianda se mueve de una helader a a otra
  // Case 3: La vianda se retira de la heladera y se come
  public void setHeladera(Heladera heladera) {
    EventoFactory.crearEvento(this.heladera, TipoEvento.RETIRO);
    EventoFactory.crearEvento(heladera, TipoEvento.INGRESO);
    this.heladera = heladera;
  }
}
