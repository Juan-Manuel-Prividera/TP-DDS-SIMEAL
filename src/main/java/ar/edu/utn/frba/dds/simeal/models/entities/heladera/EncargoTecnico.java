package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Tecnico;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Setter
@Table(name = "encargo_tecnico")
@NoArgsConstructor
@AllArgsConstructor
public class EncargoTecnico extends Persistente {
  @OneToOne
  @JoinColumn(name = "incidente_id", referencedColumnName = "id")
  private Incidente incidente;

  @ManyToOne
  @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
  private Tecnico tecnico;

  @Column(name = "aceptado")
  private Boolean aceptado;

  @Column(name = "visitas_hechas")
  private int visitasHechas;

  @Column(name = "imagen")
  private String imagen;

  public EncargoTecnico(EncargoTecnico encargoTecnico, Tecnico tecnico) {
    this.incidente = encargoTecnico.getIncidente();
    this.imagen = encargoTecnico.getImagen();
    this.aceptado = null; // Pendiente
    this.visitasHechas = 0;
    this.tecnico = tecnico;
  }

  public void incrementVisitasHechas() {
    visitasHechas++;
  }


}
