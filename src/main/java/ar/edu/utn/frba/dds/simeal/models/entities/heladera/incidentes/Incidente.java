package ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDateTime;


@Entity @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Incidente extends Persistente {
  public abstract String getNotificacion();
  public abstract Heladera getHeladera();
  public abstract LocalDateTime getFechaHora();
}
