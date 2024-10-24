package ar.edu.utn.frba.dds.simeal.controllers.heladera;

import ar.edu.utn.frba.dds.simeal.models.dtos.HeladeraDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.SuscripcionDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.HayMuchasViandas;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.HuboUnDesperfecto;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.QuedanPocasViandas;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.SuscripcionesRepository;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SuscripcionController {
  private final Repositorio repositorio;
  private final SuscripcionesRepository suscripcionesRepository;

  public SuscripcionController(Repositorio repositorio, SuscripcionesRepository suscripcionesRepository) {
    this.repositorio = repositorio;
    this.suscripcionesRepository = suscripcionesRepository;
  }

  public void index(Context ctx) {
    HashMap<String,Object> model = new HashMap<>();
    setNavBar(model,ctx);
    model.put("titulo", "Home Suscripcion");
    Heladera heladera = (Heladera) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")), Heladera.class);
    Colaborador colaborador = (Colaborador)  repositorio
      .buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);


    model.put("heladera", new HeladeraDTO(heladera,0D));
    ctx.render("/heladeras/suscripcion_heladera.hbs",model);
  }

  public void altaSuscripcion(Context ctx) {
    Heladera heladera = (Heladera) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")), Heladera.class);
    Colaborador colaborador = (Colaborador) repositorio
      .buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);

    Boolean checkViandasInsuficientes = Objects.equals(ctx.formParam("checkViandasInsuficientes"), "true");
    Boolean checkMuchasViandas = Objects.equals(ctx.formParam("checkMuchasViandas"), "true");
    Boolean checkDesperfecto = Objects.equals(ctx.formParam("checkDesperfecto"), "true");

    if (checkViandasInsuficientes){
      Suscripcion suscripcion = Suscripcion.builder()
        .suscriptor(colaborador)
        .heladera(heladera)
        .cercaniaNecesaria(1000)
        .cantidadViandasCriticas(Integer.parseInt(ctx.formParam("cantViandasInsuficientes")))
        .notificacion(new QuedanPocasViandas())
        .build();
      suscripcionesRepository.guardar(suscripcion);
    }
    if (checkMuchasViandas){
      Suscripcion suscripcion = Suscripcion.builder()
        .suscriptor(colaborador)
        .heladera(heladera)
        .cercaniaNecesaria(1000)
        .cantidadViandasCriticas(Integer.parseInt(ctx.formParam("cantMuchasViandas")))
        .notificacion(new HayMuchasViandas())
        .build();
      suscripcionesRepository.guardar(suscripcion);
    }
    if (checkDesperfecto) {
      Suscripcion suscripcion = Suscripcion.builder()
        .suscriptor(colaborador)
        .heladera(heladera)
        .cercaniaNecesaria(1000)
        .notificacion(new HuboUnDesperfecto())
        .build();
      suscripcionesRepository.guardar(suscripcion);
    }
    ctx.redirect("/heladera/suscribirse/" + heladera.getId());
  }

  public void buscarSuscripciones(Context ctx) {
    HashMap<String,Object> model = new HashMap<>();
    setNavBar(model,ctx);
    Colaborador colaborador = (Colaborador) repositorio.buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
    List<Suscripcion> suscripciones = suscripcionesRepository.buscarPor(colaborador);
    List<SuscripcionDTO> suscripcionesDTO = new ArrayList<>();
    for (Suscripcion suscripcion : suscripciones) {
      if (suscripcion.getActivo())
        suscripcionesDTO.add(new SuscripcionDTO(suscripcion));
    }
    model.put("colaborador_id", colaborador.getId());
    model.put("suscripciones", suscripcionesDTO);
    ctx.render("/heladeras/suscripciones.hbs",model);
  }

  public void borrarSuscripcion(Context ctx) {
    HashMap<String,Object> model = new HashMap<>();
    setNavBar(model,ctx);
    try {
      Suscripcion suscripcion = (Suscripcion) suscripcionesRepository
        .buscarPorId(Long.valueOf(ctx.pathParam("suscripcion_id")), Suscripcion.class);
      suscripcionesRepository.desactivar(suscripcion);
      suscripcionesRepository.refresh(suscripcion);
      ctx.status(200);
    } catch (Exception e) {
      ctx.status(500);
    }
  }

  private void setNavBar(HashMap<String,Object> model, Context app) {
    if (app.sessionAttribute("user_type").equals("HUMANO"))
      model.put("esHumano", "true");
    else if (app.sessionAttribute("user_type").equals("JURIDICO"))
      model.put("esJuridico", "true");

    model.put("user_type",app.sessionAttribute("user_type"));


    model.put("heladeras", "seleccionado");
    model.put("username", app.sessionAttribute("user_name"));

  }
}
