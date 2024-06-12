package ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import java.time.LocalDateTime;

public class FallaTecnica implements Incidente{
  LocalDateTime fechaYHora;
  Colaborador colaborador;
  String descripcion;
  String imagen;
}
