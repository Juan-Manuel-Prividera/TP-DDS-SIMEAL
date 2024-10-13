package ar.edu.utn.frba.dds.simeal.controllers.heladera;

import ar.edu.utn.frba.dds.simeal.models.dtos.HeladeraDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.HayMuchasViandas;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.HuboUnDesperfecto;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.QuedanPocasViandas;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.SuscripcionesRepository;
import io.javalin.http.Context;

import java.util.HashMap;
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
    setNavBar(model);
    model.put("titulo", "Home Suscripcion");
    Heladera heladera = (Heladera) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")), Heladera.class);
    // TODO: SACAR DE LA SESION
    Colaborador colaborador = (Colaborador)  repositorio
      .buscarPorId(1L, Colaborador.class);


    model.put("heladera", new HeladeraDTO(heladera,0D));
    ctx.render("/heladeras/suscripcion_heladera.hbs",model);
  }

  public void altaSuscripcion(Context ctx) {
    Heladera heladera = (Heladera) repositorio
      .buscarPorId(Long.valueOf(ctx.pathParam("heladera_id")), Heladera.class);
    // TODO: SACAR DE LA SESION
    Colaborador colaborador = (Colaborador) repositorio
      .buscarPorId(1L, Colaborador.class);

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



  private void setNavBar(HashMap<String,Object> model) {
    // TODO: Hacer un if con el rol de la sesion
    model.put("esHumano", true);
    // TODO: Esto esta en una cookie
    model.put("user_type","humano");

    model.put("heladeras", "seleccionado");
    model.put("username", "sacar de la sesion :)");
  }
}
