package ar.edu.utn.frba.dds.simeal.controllers.heladera;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.SolicitudOperacionHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.TipoOperacion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.SolicitudOperacionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.TarjetaColaboradorRepository;
import ar.edu.utn.frba.dds.simeal.service.broker.MQTTPublisherSolicitudes;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SolicitudHeladeraController {
  private final Repositorio repositorio;
  private final TarjetaColaboradorRepository tarjetaColaboradorRepository;
  private final  SolicitudOperacionRepository solicitudOperacionRepository;

  public SolicitudHeladeraController(Repositorio repositorio, TarjetaColaboradorRepository tarjetaColaboradorRepository, SolicitudOperacionRepository solicitudOperacionRepository) {
    this.repositorio = repositorio;
    this.solicitudOperacionRepository = solicitudOperacionRepository;
    this.tarjetaColaboradorRepository = tarjetaColaboradorRepository;
  }
  // GET /solicitud/{tarjeta_personal_id}
  public void index(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model,ctx);
    String failed = ctx.queryParam("failed");
    String action = ctx.queryParam("action");

    if (Objects.equals(failed,"false") && Objects.equals(action,"create")){
      model.put("popup_title", "Solicitud realizada correctamente");
      model.put("popup_ruta", "/tarjeta");
    } else if (Objects.equals(failed,"true") && Objects.equals(action,"create")){
      model.put("popup_title", "Error al realizar la solicitud");
      model.put("popup_message", "Ya tiene una solicitud pendiente de esa heladera o los campos ingresados no son validos");
      model.put("popup_ruta", "/solicitud/" + ctx.pathParam("tarjeta_personal_id"));
    }

    ctx.render("solicitudOperacionHeladera.hbs", model);
  }

  // POST /solicitud/{tarjeta_personal_id}
  public void create(Context ctx) {
    Long tarjetaId = Long.parseLong(ctx.pathParam("tarjeta_personal_id"));
    try {
      TarjetaColaborador tarjetaColaborador = (TarjetaColaborador) tarjetaColaboradorRepository
        .buscarPorId(tarjetaId, TarjetaColaborador.class);

      SolicitudOperacionHeladera solicitudOperacionHeladera = SolicitudOperacionHeladera.builder()
        .tipoOperacion(TipoOperacion.valueOf(ctx.formParam("tipoOperacion")))
        .heladera((Heladera) repositorio.buscarPorId(Long.valueOf(ctx.formParam("id_heladera")), Heladera.class))
        .horaSolicitud(LocalDateTime.now())
        .horaDeRealizacion(LocalDateTime.parse(ctx.formParam("horario")))
        .cantViandas(Integer.valueOf(ctx.formParam("cantidadViandas")))
        .tarjetaColaborador(tarjetaColaborador)
        .build();

      if (!tieneCosasParaHacer(tarjetaColaborador, solicitudOperacionHeladera.getTipoOperacion(), solicitudOperacionHeladera.getHeladera())) {
        throw new Exception("No tiene nada que hacer en las heladeras :(");
      }

      List<SolicitudOperacionHeladera> solicitudesExistentes = solicitudOperacionRepository.getPorTarjetaColaborador(tarjetaId);
      for (SolicitudOperacionHeladera s : solicitudesExistentes) {
        if (s.getActivo() && s.getTipoOperacion().equals(solicitudOperacionHeladera.getTipoOperacion()) &&
          s.getHeladera().getId().equals(solicitudOperacionHeladera.getHeladera().getId())) {
          // Ya hay una solicitud de ese tipo para esa healdera
          throw new RuntimeException("Se quizo realizar una solicitud ya existente");
        }
      }

      MQTTPublisherSolicitudes.realizarSolicitud(solicitudOperacionHeladera);
      repositorio.guardar(solicitudOperacionHeladera);
      ctx.redirect("/solicitud/" + tarjetaId + "/?failed=false&action=create");
    } catch (Exception e) {
      ctx.redirect("/solicitud/" + tarjetaId + "/?failed=true&action=create");
    }
  }


  private void setNavBar(HashMap<String, Object> model, Context ctx) {
    model.put("tarjetas", "seleccionado");
    model.put("user_type", ctx.sessionAttribute("user_type").toString().toLowerCase());

    if (ctx.sessionAttribute("user_type") == "HUMANO")
      model.put("esHumano","true");

    model.put("tarjeta_personal_id", ctx.pathParam("tarjeta_personal_id"));
    model.put("username", ctx.sessionAttribute("user_name"));
  }

  private boolean tieneCosasParaHacer(TarjetaColaborador tarjetaColaborador, TipoOperacion tipoOperacion, Heladera heladera) {
    ColaboracionRepository colaboracionRepository = (ColaboracionRepository) ServiceLocator
      .getRepository(ColaboracionRepository.class);

    List<DonarVianda> donacionesViandas = (List<DonarVianda>) colaboracionRepository
      .getPorColaborador(tarjetaColaborador.getColaborador().getId(), DonarVianda.class);
    List<DistribuirVianda> distribucionVianda = (List<DistribuirVianda>) colaboracionRepository
      .getPorColaborador(tarjetaColaborador.getColaborador().getId(), DistribuirVianda.class);

    for (DonarVianda d : donacionesViandas) {
      if (d.getActivo() && !d.getVianda().getEntregada() && tipoOperacion.equals(TipoOperacion.INGRESO)) {
        return true;
      }
    }

    for (DistribuirVianda d : distribucionVianda) {
      if (d.getActivo() && d.getOrigen().getId().equals(heladera.getId()) && tipoOperacion.equals(TipoOperacion.RETIRO)) {
        return true;
      }
      if (d.getActivo() && d.getDestino().getId().equals(heladera.getId()) && tipoOperacion.equals(TipoOperacion.INGRESO)) {
        return true;
      }
    }

    return false;
  }
}
