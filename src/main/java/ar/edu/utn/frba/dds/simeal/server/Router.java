package ar.edu.utn.frba.dds.simeal.server;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.controllers.AdminController;
import io.javalin.Javalin;

public class Router {
  public static void init(Javalin app) {
    // app.get("/ruta", MetodoController);
    app.get("/simeal/migracion", ServiceLocator.getController(AdminController.class)::index);
    app.get("/simeal/reportes", ServiceLocator.getController(AdminController.class)::reportes);
    app.get("/simeal/cambiarmodo", ServiceLocator.getController(AdminController.class)::cambiarmodo);
    app.post("/simeal/migracion/upload", ServiceLocator.getController(AdminController.class)::migrarColaboraciones);
  }
}
