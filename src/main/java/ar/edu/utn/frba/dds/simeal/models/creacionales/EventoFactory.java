package ar.edu.utn.frba.dds.simeal.models.creacionales;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.controllers.AdministradorDeEventos;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.SugerenciaHeladeras;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.Evento;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.HayMuchasViandas;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.HuboUnDesperfecto;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.QuedanPocasViandas;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;

public class EventoFactory {

    public static void crearEvento(Heladera heladera, TipoEvento tipoEvento) {
        AdministradorDeEventos administradorDeEventos = crearAdministrador();
        Repositorio heladeraRepository = ServiceLocator.getRepository(Repositorio.class);
        if(heladera == null) return;
        administradorDeEventos.huboUnEvento(
        switch (tipoEvento) {
            case RETIRO ->  new Evento(heladera, TipoEvento.RETIRO, new QuedanPocasViandas());
            case INGRESO -> new Evento(heladera, TipoEvento.INGRESO, new HayMuchasViandas());
            case INCIDENTE -> new Evento(heladera, TipoEvento.INCIDENTE, crearHuboUnDesperfecto(heladera));

        });
    }

    private static HuboUnDesperfecto crearHuboUnDesperfecto(Heladera heladera) {
       HuboUnDesperfecto huboUnDesperfecto = new HuboUnDesperfecto();
       huboUnDesperfecto.setSugerenciaHeladeras(new SugerenciaHeladeras(heladera.getUbicacion(), heladera.getHeladerasCercanas()));
       return huboUnDesperfecto;
    }
    public static AdministradorDeEventos crearAdministrador() {
        return new AdministradorDeEventos();
    }
}
