package ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "falla_tecnica")
public class FallaTecnica extends Persistente implements Incidente{
  @ManyToOne
  @JoinColumn(referencedColumnName = "id", name = "heladera_id")
  private Heladera heladera;

  @Column(name="descripcion")
  private String descripcion;

  @Column(name="fecha_hora")
  private LocalDateTime fechaHora;

  @ManyToOne
  @JoinColumn(referencedColumnName = "id", name = "colaborador_id")
  private Colaborador colaborador;

  @Column(name="imagen")
  private String imagen;

  @Override
  public String getNotificacion() {
    DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    return "\t- Fecha: " + fechaHora.format(formatterDia)
        + "\n\t- Hora: " + fechaHora.format(formatterHora)
        + "\n\t- Colaborador: " + colaborador.getNombre() + " " + colaborador.getApellido()
        + "\n\t- Descripcion: " + descripcion
        + "\n\t- ImagePath: " + imagen;
  }
}
