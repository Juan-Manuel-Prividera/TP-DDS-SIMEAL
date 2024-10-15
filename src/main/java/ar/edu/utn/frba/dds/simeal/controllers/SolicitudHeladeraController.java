package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.SolicitudOperacionHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.TipoOperacion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.TarjetaColaboradorRepository;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class SolicitudHeladeraController {
  private Repositorio repositorio;
  private TarjetaColaboradorRepository tarjetaColaboradorRepository;

  public SolicitudHeladeraController(Repositorio repositorio, TarjetaColaboradorRepository tarjetaColaboradorRepository) {
    this.repositorio = repositorio;
    this.tarjetaColaboradorRepository = tarjetaColaboradorRepository;
  }

  public void index(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model,ctx);
    ctx.render("solicitudOperacionHeladera.hbs", model);
  }

  public void create(Context ctx) {
    TarjetaColaborador tarjetaColaborador = (TarjetaColaborador) tarjetaColaboradorRepository
      .buscarPorId(Long.parseLong(ctx.pathParam("tarjeta_personal_id")), TarjetaColaborador.class);

    SolicitudOperacionHeladera solicitudOperacionHeladera = SolicitudOperacionHeladera.builder()
      .tipoOperacion(TipoOperacion.valueOf(ctx.formParam("tipoOperacion")))
      .heladera(buscarHeladeraPorNombre(ctx.formParam("heladera")))
      .horaSolicitud(LocalDateTime.now())
      .horaInicio(LocalDateTime.parse(ctx.formParam("horario")))
      .cantViandas(Integer.valueOf(ctx.formParam("cantidadViandas")))
      .tarjetaColaborador(tarjetaColaborador)
      .build();

    repositorio.guardar(solicitudOperacionHeladera);
    ctx.redirect("/tarjeta");
  }
  // TODO: Buscar forma de hacerlo por ID
  private Heladera buscarHeladeraPorNombre(String heladeraNombre) {
    List<Heladera> heladeras = (List<Heladera>) repositorio.obtenerTodos(Heladera.class);
    for (Heladera h : heladeras) {
      if (h.getNombre().equals(heladeraNombre)) {
        return h;
      }
    }
    return null;
  }

  private void setNavBar(HashMap<String, Object> model, Context ctx) {
    model.put("tarjetas", "seleccionado");
    model.put("user_type", ctx.sessionAttribute("user_type").toString().toLowerCase());
    if (ctx.sessionAttribute("user_type") == "HUMANO")
      model.put("esHumano","true");

    model.put("tarjeta_personal_id", ctx.pathParam("tarjeta_personal_id"));
    model.put("username", ctx.sessionAttribute("username"));
  }
}
