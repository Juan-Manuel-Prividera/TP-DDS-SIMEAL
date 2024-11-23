package ar.edu.utn.frba.dds.simeal.controllers.colaboraciones;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;

import java.time.LocalDate;

public class DonarViandaController {
    public void create(Colaborador colaborador, Vianda vianda) {
        DonarVianda donarVianda = new DonarVianda(colaborador, LocalDate.now(), vianda);
        Repositorio repositorio = ServiceLocator.getRepository(Repositorio.class);
        repositorio.guardar(donarVianda);
    }
}
