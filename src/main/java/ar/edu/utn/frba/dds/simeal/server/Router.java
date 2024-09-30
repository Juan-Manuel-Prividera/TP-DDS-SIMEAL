package ar.edu.utn.frba.dds.simeal.server;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.controllers.TarjetasController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.CambioModoController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.MigracionController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.ReportesController;
import ar.edu.utn.frba.dds.simeal.controllers.OfertasController;
import io.javalin.Javalin;

public class Router {
  public static void init(Javalin app) {

    // ***************  Admin   ***************
    app.get("/simeal/migracion", ServiceLocator.getController(MigracionController.class)::index);
    app.get("/simeal/reportes", ServiceLocator.getController(ReportesController.class)::reportes);
    app.get("/simeal/cambiarmodo", ServiceLocator.getController(CambioModoController.class)::cambiarmodo);
    app.post("/simeal/migracion/upload", ServiceLocator.getController(MigracionController.class)::migrarColaboraciones);

    // ***************  Tarjetas   ***************
    app.get("/simeal/tarjetas", ServiceLocator.getController(TarjetasController.class)::index);
    // ***************  Heladeras   ***************

    // ***************  Ofertas  ***************
    app.get("/simeal/{usr_type}/ofertas", ServiceLocator.getController(OfertasController.class)::index);
    app.get("/simel/{usr_type}/oferta/{oferta_id}", ServiceLocator.getController(OfertasController.class)::show);

  }
}
