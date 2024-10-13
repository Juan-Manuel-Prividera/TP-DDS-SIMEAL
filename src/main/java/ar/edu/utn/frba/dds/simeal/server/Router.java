package ar.edu.utn.frba.dds.simeal.server;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.controllers.admin.FormularioController;
import ar.edu.utn.frba.dds.simeal.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.simeal.controllers.OfertasController;
import ar.edu.utn.frba.dds.simeal.controllers.PersonaVulnerableController;
import ar.edu.utn.frba.dds.simeal.controllers.TarjetasController;
import ar.edu.utn.frba.dds.simeal.controllers.UsuariosController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.CambioModoController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.MigracionController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.ReportesController;
import ar.edu.utn.frba.dds.simeal.controllers.heladera.IncidenteController;
import ar.edu.utn.frba.dds.simeal.controllers.heladera.SuscripcionController;
import ar.edu.utn.frba.dds.simeal.handlers.IndexHandler;
import ar.edu.utn.frba.dds.simeal.handlers.LoginHandler;
import ar.edu.utn.frba.dds.simeal.handlers.LogoutHandler;
import ar.edu.utn.frba.dds.simeal.handlers.RegistroHandler;
import io.javalin.Javalin;

public class Router {
  public static void init(Javalin app) {
    // ***************  HomePage   ***************
    app.get("humano/home", context -> context.render("humano_home.hbs"));
    app.get("juridico/home", context -> context.render("juridico_home.hbs"));

    // ***************  Admin   ***************
    app.get("migracion", ServiceLocator.getController(MigracionController.class)::index);
    app.get("reportes", ServiceLocator.getController(ReportesController.class)::reportes);
    app.get("cambiarmodo", ServiceLocator.getController(CambioModoController.class)::cambiarmodo);
    app.post("migracion/upload", ServiceLocator.getController(MigracionController.class)::migrarColaboraciones);

    app.get("formularios", ServiceLocator.getController(FormularioController.class)::index);
    app.post("formulario", ServiceLocator.getController(FormularioController.class)::crearFormulario);
    app.get("formulario/{formulario_id}", ServiceLocator.getController(FormularioController.class)::editarFormulario);
    app.post("formulario/{formulario_id}/pregunta", ServiceLocator.getController(FormularioController.class)::crearPregunta);
    app.delete("formulario/{formulario_id}/pregunta/{pregunta_id}", ServiceLocator.getController(FormularioController.class)::borrarPregunta);

    // ***************  Tarjetas   ***************
    app.get("tarjeta", ServiceLocator.getController(TarjetasController.class)::index);
    app.get("tarjeta/new", ServiceLocator.getController(TarjetasController.class)::indexNewTarjeta);
    app.post("tarjeta/new", ServiceLocator.getController(PersonaVulnerableController.class)::create);

    app.get("tarjeta/delete", ServiceLocator.getController(TarjetasController.class)::indexBorrarTarjeta);
    app.post("tarjeta/delete/{numeroTarjeta}", ServiceLocator.getController(TarjetasController.class)::delete);

    app.get("tarjeta/update", ServiceLocator.getController(TarjetasController.class)::indexUpdateTarjeta);
    app.post("tarjeta/update/{numeroTarjeta}", ServiceLocator.getController(TarjetasController.class)::update);

    // ***************  Heladeras   ***************
    app.get("{usr_type}/heladera", ServiceLocator.getController(HeladeraController.class)::index);
    app.get("heladera/{heladera_id}", ServiceLocator.getController(HeladeraController.class)::getEstadoHeladera);

    app.get("heladera/reportar/{heladera_id}", ServiceLocator.getController(HeladeraController.class)::indexReporteFallo);
    app.post("heladera/reportar/{heladera_id}", ServiceLocator.getController(HeladeraController.class)::reportarFallo);
    app.get("heladera/incidentes/{heladera_id}", ServiceLocator.getController(IncidenteController.class)::index);

    app.get("heladera/suscribirse/{heladera_id}", ServiceLocator.getController(SuscripcionController.class)::index);
    app.post("heladera/suscribirse/{heladera_id}", ServiceLocator.getController(SuscripcionController.class)::altaSuscripcion);

    app.get("/heladeras", ServiceLocator.getController(HeladeraController.class)::getAll);

    // ***************  Ofertas  ***************
    app.get("{usr_type}/ofertas", ServiceLocator.getController(OfertasController.class)::index);
    app.get("{usr_type}/oferta/{oferta_id}", ServiceLocator.getController(OfertasController.class)::show);

    // **************** Usuarios *****************
    app.post("login", new LoginHandler()::handle);
    app.get("logout", new LogoutHandler()::handle);
    app.get("registro", ctx->{ctx.render("registro.hbs");});
    app.get("registro/{rol}", new RegistroHandler()::handle);
    app.post("user/create/{rol}", ServiceLocator.getController(UsuariosController.class)::create);

    // ****************** Colaboraciones ******************

    // ****************** Index ******************
    app.get("/", new IndexHandler()::handle);

  }
}
