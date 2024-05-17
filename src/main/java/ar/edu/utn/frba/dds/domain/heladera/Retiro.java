package ar.edu.utn.frba.dds.domain.heladera;

import ar.edu.utn.frba.dds.domain.personas.documentacion.Tarjeta;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;

import java.time.LocalDate;

public class Retiro {
    private LocalDate fechaRetiro;
    private Heladera heladera;
    private Vianda vianda;
    private Tarjeta tarjeta;
}
