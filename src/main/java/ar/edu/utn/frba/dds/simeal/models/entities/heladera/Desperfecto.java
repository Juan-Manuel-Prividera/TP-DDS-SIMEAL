package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Medicion;

import java.time.LocalDate;

public class Desperfecto {
  private String descripcion;
  private LocalDate fechaDeOcurrencia;
  private Medicion comoSeDetecto;
}
