package ar.edu.utn.frba.dds.simeal.server;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.controllers.AdminController;
import ar.edu.utn.frba.dds.simeal.controllers.OfertasController;
import io.javalin.Javalin;

public class Router {
  public static void init(Javalin app) {
    // app.get("/ruta", MetodoController);
    app.get("/simeal/migracion", ServiceLocator.getController(AdminController.class)::index);
    app.get("/simeal/reportes", ServiceLocator.getController(AdminController.class)::reportes);
    app.get("/simeal/cambiarmodo", ServiceLocator.getController(AdminController.class)::cambiarmodo);
    app.post("/simeal/migracion/upload", ServiceLocator.getController(AdminController.class)::migrarColaboraciones);
    // Esto está horrible, lo dejó así por ahora para poder avanzar
    app.get("/simeal/{usr_type}/ofertas", ServiceLocator.getController(OfertasController.class)::index);
    app.get("/simel/{usr_type}/oferta/{oferta_id}", ServiceLocator.getController(OfertasController.class)::show);
  }
}
