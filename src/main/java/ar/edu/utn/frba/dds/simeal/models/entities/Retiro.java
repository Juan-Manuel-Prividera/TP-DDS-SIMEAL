package ar.edu.utn.frba.dds.simeal.models.entities;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Tarjeta;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Retiro {
    private LocalDate fechaRetiro;
    private Heladera heladera;
    private Tarjeta tarjeta;
}
