package ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "alerta")
@Entity
public class Alerta extends Persistente implements Incidente {
  @ManyToOne
  @JoinColumn(name="heladera_id", referencedColumnName = "id")
  private Heladera heladera;
  @Column(name = "descripcion")
  private String descripcion;
  @Column(name = "fecha_hora")
  private LocalDateTime fechaHora;
  @Column(name = "tipo_alerta")
  @Enumerated(EnumType.STRING)
  private TipoAlerta tipoAlerta;


  @Override
  public String getNotificacion() {
    DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    return "\t- Fecha: " + fechaHora.format(formatterDia)
        + "\n\t- Hora: " + fechaHora.format(formatterHora)
        + "\n\t- Alerta de tipo: " + tipoAlerta.name()
        + "\n\t- Descripción: " + descripcion;
  }

  public Alerta(Heladera heladera, String descripcion, TipoAlerta tipoAlerta) {
    this.heladera = heladera;
    this.descripcion = descripcion;
    this.fechaHora = LocalDateTime.now();
    this.tipoAlerta = tipoAlerta;
  }

}


  /*
  Quizás podría tener la medición asociada ?:
      La de temperatura que fue crítica,
      la de movimiento que siempre se reporta como incidente
      y la última medición que registró el sensor (si es alerta de conexión)
   */

