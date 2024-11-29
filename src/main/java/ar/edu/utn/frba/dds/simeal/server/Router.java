package ar.edu.utn.frba.dds.simeal.server;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.controllers.*;
import ar.edu.utn.frba.dds.simeal.controllers.admin.CambioModoController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.FormularioController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.MigracionController;
import ar.edu.utn.frba.dds.simeal.controllers.admin.ReportesController;
import ar.edu.utn.frba.dds.simeal.controllers.colaboraciones.*;
import ar.edu.utn.frba.dds.simeal.controllers.heladera.*;
import ar.edu.utn.frba.dds.simeal.controllers.tarjetas.PersonaVulnerableController;
import ar.edu.utn.frba.dds.simeal.controllers.tarjetas.TarjetasController;
import ar.edu.utn.frba.dds.simeal.controllers.tecnico.EncargoController;
import ar.edu.utn.frba.dds.simeal.controllers.tecnico.TecnicoController;
import ar.edu.utn.frba.dds.simeal.controllers.tecnico.VisitaController;
import ar.edu.utn.frba.dds.simeal.handlers.*;
import ar.edu.utn.frba.dds.simeal.server.exception_handlers.NotFoundException;
import ar.edu.utn.frba.dds.simeal.utils.DDMetricsUtils;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

import java.nio.charset.StandardCharsets;

public class Router {
  public static void init(Javalin app) {
    // ***************  HomePage   ***************
    app.get("home", ServiceLocator.getController(HomeHandler.class)::handle);

    // ***************  Admin   ***************
    app.get("migracion", ServiceLocator.getController(MigracionController.class)::index);
    app.post("migracion/upload", ServiceLocator.getController(MigracionController.class)::migrarColaboraciones);
    app.get("reportes", ServiceLocator.getController(ReportesController.class)::reportes);
    app.get("reporte", ServiceLocator.getController(ReportesController.class)::generarReporte);
    app.get("cambiarmodo", ServiceLocator.getController(CambioModoController.class)::cambiarmodo);

    app.get("formularios", ServiceLocator.getController(FormularioController.class)::index);
    app.post("formulario", ServiceLocator.getController(FormularioController.class)::crearFormulario);
    app.get("formulario/{formulario_id}", ServiceLocator.getController(FormularioController.class)::editarFormulario);
    app.post("formulario/{formulario_id}/pregunta", ServiceLocator.getController(FormularioController.class)::crearPregunta);
    app.post("formulario/{formulario_id}/pregunta/{pregunta_id}", ServiceLocator.getController(FormularioController.class)::borrarPregunta);
    app.delete("formulario/{formulario_id}", ServiceLocator.getController(FormularioController.class)::borrarFormulario);

   // ***************  Tecnicos  ***************
    app.get("tecnico", ServiceLocator.getController(TecnicoController.class)::index);
    app.get("registro/tecnico", ServiceLocator.getController(TecnicoController.class)::registroIndex);
    app.post("tecnico", ServiceLocator.getController(TecnicoController.class)::crearTecnico);
    app.delete("tecnico/{tecnico_id}", ServiceLocator.getController(TecnicoController.class)::borrarTecnico);

    app.get("encargo/{encargo_id}/aceptado", ServiceLocator.getController(EncargoController.class)::aceptarEncargo);
    app.get("encargo/{encargo_id}/rechazado", ServiceLocator.getController(EncargoController.class)::rechazarEncargo);

    app.get("tecnico/home", ServiceLocator.getController(VisitaController.class)::index);
    app.get("{encargo_id}/visita", ServiceLocator.getController(VisitaController.class)::indexRegistroVisita);
    app.post("{encargo_id}/visita", ServiceLocator.getController(VisitaController.class)::registrar);

    // TODO: Arreglar estas rutas
    // ***************  Tarjetas   ***************
    app.get("tarjeta", ServiceLocator.getController(TarjetasController.class)::index);
    app.get("tarjeta/new", ServiceLocator.getController(TarjetasController.class)::indexNewTarjeta);
    app.post("tarjeta/new", ServiceLocator.getController(PersonaVulnerableController.class)::create);

    app.get("tarjeta/delete", ServiceLocator.getController(TarjetasController.class)::indexBorrarTarjeta);
    app.post("tarjeta/delete/{numeroTarjeta}", ServiceLocator.getController(TarjetasController.class)::delete);

    app.get("tarjeta/update", ServiceLocator.getController(TarjetasController.class)::indexUpdateTarjeta);
    app.post("tarjeta/update/{numeroTarjeta}", ServiceLocator.getController(TarjetasController.class)::update);

    // ***************  Solicitud de operacion heladera  ***************
    app.get("solicitud/{tarjeta_personal_id}", ServiceLocator.getController(SolicitudHeladeraController.class)::index);
    app.post("solicitud/{tarjeta_personal_id}", ServiceLocator.getController(SolicitudHeladeraController.class)::create);

    // ***************  Heladeras   ***************
    app.get("heladera", ServiceLocator.getController(HeladeraController.class)::index);
    app.get("heladera/{heladera_id}", ServiceLocator.getController(HeladeraController.class)::getEstadoHeladera);

    app.get("heladera/reportar/{heladera_id}", ServiceLocator.getController(HeladeraController.class)::indexReporteFallo);
    app.post("heladera/reportar/{heladera_id}", ServiceLocator.getController(HeladeraController.class)::reportarFallo);
    app.get("heladera/incidentes/{heladera_id}", ServiceLocator.getController(IncidenteController.class)::index);
    app.get("heladera/visitas/{heladera_id}", ServiceLocator.getController(HeladeraController.class)::getVisitas);

    app.get("heladera/suscribirse/{heladera_id}", ServiceLocator.getController(SuscripcionController.class)::index);
    app.post("heladera/suscribirse/{heladera_id}", ServiceLocator.getController(SuscripcionController.class)::altaSuscripcion);
    app.delete("suscripcion/{suscripcion_id}", ServiceLocator.getController(SuscripcionController.class)::borrarSuscripcion);
    app.get("suscripciones", ServiceLocator.getController(SuscripcionController.class)::buscarSuscripciones);

    app.get("heladeras", ServiceLocator.getController(HeladeraController.class)::getAll);

    // ***************  Ofertas  ***************
    app.get("ofertas", ServiceLocator.getController(OfertasController.class)::index);
    app.get("ofertas/misCanjes", ServiceLocator.getController(OfertasController.class)::canjes);
    app.get("ofertas/misOfertas", ServiceLocator.getController(OfertasController.class)::selfOffers);
    app.get("ofertas/misOfertas/publicar", ServiceLocator.getController(OfertasController.class)::publicar);
    app.post("ofertas/misOfertas/publicar", ServiceLocator.getController(OfertasController.class)::create);
    app.get("ofertas/misOfertas/{oferta_id}", ServiceLocator.getController(OfertasController.class)::selfOffersSelected);
    //TODO: Como checkear que oferta_id es una oferta suya?
    app.get("ofertas/misOfertas/{oferta_id}/modificar", ServiceLocator.getController(OfertasController.class)::modificar);
    app.post("ofertas/misOfertas/{oferta_id}/modificar", ServiceLocator.getController(OfertasController.class)::updateOferta);
    app.delete("ofertas/misOfertas/{oferta_id}/modificar", ServiceLocator.getController(OfertasController.class)::deleteOferta);
    app.post("ofertas/comprar", ServiceLocator.getController(OfertasController.class)::comprar);

    app.get("ofertas/{oferta_id}", ServiceLocator.getController(OfertasController.class)::show);

    // **************** Localidades *****************
    app.get("localidades", ServiceLocator.getController(UbicacionController.class)::getLocalidades);

    // **************** Usuarios *****************
    app.post("login", new LoginHandler()::handle);
    app.get("logout", new LogoutHandler()::handle);
    app.get("registro", ctx->{ctx.render("registro.hbs");});
    app.get("registro/{rol}", ServiceLocator.getController(RegistroHandler.class)::handle);
    app.post("user/create/{rol}", ServiceLocator.getController(UsuariosController.class)::create);

    // ****************** Colaboraciones ******************
    app.get("colaboraciones", ServiceLocator.getController(ColaboracionesController.class)::index);
    app.get("colaboraciones/historial", ServiceLocator.getController(ColaboracionesController.class)::indexHistorial);
    app.get("colaboraciones/configuracion", ServiceLocator.getController(ColaboracionesController.class)::indexConfiguracion);
    app.post("colaboraciones/configuracion", ServiceLocator.getController(ColaboracionesController.class)::configurarPreferencias);

    app.get("colaboracion/donarDinero", ServiceLocator.getController(DonarDineroController.class)::index);
    app.post("colaboracion/donarDinero", ServiceLocator.getController(DonarDineroController.class)::create);

    app.get("colaboracion/donarVianda", ServiceLocator.getController(DonarViandaController.class)::index);
    app.post("colaboracion/donarVianda", ServiceLocator.getController(ViandaController.class)::create);

    app.get("colaboracion/distribucionVianda", ServiceLocator.getController(DistribuirViandaController.class)::index);
    app.post("colaboracion/distribucionVianda", ServiceLocator.getController(DistribuirViandaController.class)::create);

    app.get("colaboracion/adherirHeladera", ServiceLocator.getController(AdherirHeladeraController.class)::index);
    app.post("colaboracion/adherirHeladera", ServiceLocator.getController(AdherirHeladeraController.class)::create);

    // ****************** Recomendacion de Colaboradores ******************
    app.get("recomendacion", ServiceLocator.getController(RecomendacionColabsController.class)::index);

    // ****************** Recomendacion de Puntos de colocacion ******************
    app.get("recomendacion/ubicaciones", ServiceLocator.getController(RecomendacionUbicacionController.class)::index);


    // ****************** Index ******************
    app.get("/", new IndexHandler()::handle);

    // *************** Not found *****************
    app.error(404, ctx -> {throw new NotFoundException(); });
    app.after(ctx -> {
      if (ctx.status() == HttpStatus.OK)
        Logger.debug("La ip '"+ctx.ip()+"' accedió a "+ java.net.URLDecoder.decode(ctx.url(), StandardCharsets.UTF_8));

      final var metricsUtils = DDMetricsUtils.getInstance();
      final var registry = metricsUtils.getRegistry();

      // Metricas
      metricsUtils.getAcceso().incrementAndGet();

    });

  }

}
