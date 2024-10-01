package ar.edu.utn.frba.dds.simeal.server;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.controllers.PersonaVulnerableController;
import ar.edu.utn.frba.dds.simeal.controllers.TarjetasController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.CambioModoController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.MigracionController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.ReportesController;
import ar.edu.utn.frba.dds.simeal.controllers.OfertasController;
import ar.edu.utn.frba.dds.simeal.handlers.LoginHandler;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Router {
  public static void init(Javalin app) {

    // ***************  Admin   ***************
    app.get("migracion", ServiceLocator.getController(MigracionController.class)::index);
    app.get("reportes", ServiceLocator.getController(ReportesController.class)::reportes);
    app.get("cambiarmodo", ServiceLocator.getController(CambioModoController.class)::cambiarmodo);
    app.post("migracion/upload", ServiceLocator.getController(MigracionController.class)::migrarColaboraciones);

    // ***************  Tarjetas   ***************
    app.get("tarjeta", ServiceLocator.getController(TarjetasController.class)::index);
    app.get("tarjeta/new", ServiceLocator.getController(TarjetasController.class)::indexNewTarjeta);
    app.post("tarjeta/new", ServiceLocator.getController(PersonaVulnerableController.class)::create);

    app.get("tarjeta/delete", ServiceLocator.getController(TarjetasController.class)::indexBorrarTarjeta);
    app.get("tarjeta/update", ServiceLocator.getController(TarjetasController.class)::indexUpdateTarjeta);

    // ***************  Heladeras   ***************

    // ***************  Ofertas  ***************
    app.get("{usr_type}/ofertas", ServiceLocator.getController(OfertasController.class)::index);
    app.get("{usr_type}/oferta/{oferta_id}", ServiceLocator.getController(OfertasController.class)::show);

    app.get("login", new LoginHandler()::handle);

  }
}
