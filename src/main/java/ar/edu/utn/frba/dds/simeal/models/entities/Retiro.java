package ar.edu.utn.frba.dds.simeal.models.entities;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Tarjeta;

import java.time.LocalDate;

public class Retiro {
    private LocalDate fechaRetiro;
    private Heladera heladera;
    private Vianda vianda;
    private Tarjeta tarjeta;
}
