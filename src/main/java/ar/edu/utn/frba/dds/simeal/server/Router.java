package ar.edu.utn.frba.dds.simeal.server;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.controllers.OfertasController;
import ar.edu.utn.frba.dds.simeal.controllers.PersonaVulnerableController;
import ar.edu.utn.frba.dds.simeal.controllers.TarjetasController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.CambioModoController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.MigracionController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.ReportesController;
import ar.edu.utn.frba.dds.simeal.handlers.IndexHandler;
import ar.edu.utn.frba.dds.simeal.handlers.LoginHandler;
import ar.edu.utn.frba.dds.simeal.handlers.LogoutHandler;
import ar.edu.utn.frba.dds.simeal.handlers.RegistroHandler;
import io.javalin.Javalin;

public class Router {
  public static void init(Javalin app) {

    // ***************  Admin   ***************
    app.get("migracion", ServiceLocator.getController(MigracionController.class)::index);
    app.get("reportes", ServiceLocator.getController(ReportesController.class)::reportes);
    app.get("cambiarmodo", ServiceLocator.getController(CambioModoController.class)::cambiarmodo);
    app.post("migracion/upload", ServiceLocator.getController(MigracionController.class)::migrarColaboraciones);

    // ***************  HomePage   ***************
    app.get("home/humano", context -> context.render("humano_home.hbs"));
    app.get("home/juridico", context -> context.render("juridico_home.hbs"));

    // ***************  Tarjetas   ***************
    app.get("tarjeta", ServiceLocator.getController(TarjetasController.class)::index);
    app.get("tarjeta/new", ServiceLocator.getController(TarjetasController.class)::indexNewTarjeta);
    app.post("tarjeta/new", ServiceLocator.getController(PersonaVulnerableController.class)::create);

    app.get("tarjeta/delete", ServiceLocator.getController(TarjetasController.class)::indexBorrarTarjeta);
    app.post("tarjeta/delete/{numeroTarjeta}", ServiceLocator.getController(TarjetasController.class)::delete);
    app.get("tarjeta/update", ServiceLocator.getController(TarjetasController.class)::indexUpdateTarjeta);
    app.post("tarjeta/update", ServiceLocator.getController(TarjetasController.class)::indexUpdateTarjeta);

    // ***************  Heladeras   ***************

    // ***************  Ofertas  ***************
    app.get("{usr_type}/ofertas", ServiceLocator.getController(OfertasController.class)::index);
    app.get("{usr_type}/oferta/{oferta_id}", ServiceLocator.getController(OfertasController.class)::show);

    // **************** Usuarios *****************
    app.post("login", new LoginHandler()::handle);
    app.get("logout", new LogoutHandler()::handle);
    app.get("registro", ctx->{ctx.render("registro.hbs");});
    app.get("registro/{tipoUsuario}", new RegistroHandler()::handle);
    app.get("/", new IndexHandler()::handle);

  }
}
