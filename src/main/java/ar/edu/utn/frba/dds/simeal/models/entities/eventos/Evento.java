package ar.edu.utn.frba.dds.simeal.models.entities.eventos;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Evento {
  private Heladera heladeraAfectada;
  private TipoEvento tipoEvento;
  private LocalDateTime fechaDeOcurrencia;
}
