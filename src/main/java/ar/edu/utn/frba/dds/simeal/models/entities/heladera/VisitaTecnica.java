package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@AllArgsConstructor
public class VisitaTecnica {
  private Heladera heladera;
  private String descripcion;
  private LocalDateTime fechaHora;
  @Getter
  private Boolean exitosa;
  private String imagen;
}
