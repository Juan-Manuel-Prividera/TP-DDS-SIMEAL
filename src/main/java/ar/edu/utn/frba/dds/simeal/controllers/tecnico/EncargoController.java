package ar.edu.utn.frba.dds.simeal.controllers.tecnico;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.EncargoTecnico;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Tecnico;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.EncargoTecnicoRepostiry;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Notificador;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;

public class EncargoController {
  private EncargoTecnicoRepostiry repoEncargo;
  private Repositorio repositorio;

  public EncargoController(EncargoTecnicoRepostiry repoEncargo, Repositorio repositorio) {
    this.repoEncargo = repoEncargo;
    this.repositorio = repositorio;
  }

  // Cuando ocurre un incidente se llama a este metodo y se asigna el encargo a un tecnico
  public void create(Incidente incidente, Tecnico tecnico) {
    EncargoTecnico encargoTecnico = EncargoTecnico.builder()
      .tecnico(tecnico) // Aca ya llega el tecnico mas cercano al incidente
      .incidente(incidente)
      .aceptado(null) // Trato el null como 'Pendiente'
      .build();

    repoEncargo.guardar(encargoTecnico);
  }
  // GET /encargo/{encargo_id}/aceptado
  // El tecnico selecciona un encargo y lo acepta
  public void aceptarEncargo(Context ctx) {
    EncargoTecnico encargoTecnico = (EncargoTecnico) repoEncargo
      .buscarPorId(Long.valueOf(ctx.pathParam("encargo_id")),EncargoTecnico.class);

    if (encargoTecnico.getAceptado() == null|| !encargoTecnico.getAceptado()) {
      encargoTecnico.setAceptado(true);
      repoEncargo.actualizar(encargoTecnico);
      repoEncargo.refresh(encargoTecnico);
      Logger.debug("Se acepto el encargo de id: " + encargoTecnico.getId());
      ctx.redirect("/tecnico/home?failed=false&action=aceptar");
    } else {
      ctx.redirect("/tecnico/home?failed=true&action=aceptar");
    }
  }

  // GET /encargo/{encargo_id}/rechazado
  public void rechazarEncargo(Context ctx) {
    EncargoTecnico encargoTecnico = (EncargoTecnico) repoEncargo
      .buscarPorId(Long.valueOf(ctx.pathParam("encargo_id")),EncargoTecnico.class);

    if (encargoTecnico.getAceptado() == null || encargoTecnico.getAceptado()) {
      encargoTecnico.setAceptado(false);
      repoEncargo.actualizar(encargoTecnico);
      repoEncargo.refresh(encargoTecnico);
      Logger.debug("Se rechazo el encargo de id: " + encargoTecnico.getId());
      ctx.redirect("/tecnico/home?failed=false&action=rechazar");

      // Como el tecnico mas cercano rechazo, hay que avisar al segundo mas cercano
      Tecnico tecnicoNuevo = remplazarTecnico(encargoTecnico.getTecnico(),encargoTecnico.getIncidente());
      Mensaje mensaje = new Mensaje(
        encargoTecnico.getIncidente().getNotificacion(),
        "Aviso de Incidente en " + encargoTecnico.getIncidente().getHeladera().getUbicacion().getStringUbi() +
          "\n Podra ver el detalle del aviso en el apartado de encargos e informar si lo acepta o rechaza.");
      Notificador.notificar(tecnicoNuevo,mensaje);

    } else ctx.redirect("/tecnico/home?failed=true&action=rechazar");
  }

  private Tecnico remplazarTecnico(Tecnico tecnicoARemplazar, Incidente incidente) {
    List<Tecnico> tecnicos = (List<Tecnico>) repositorio.obtenerTodos(Tecnico.class);
    Ubicacion ubiIncidente = incidente.getHeladera().getUbicacion();
    Tecnico tecnicoMasCercano = null;
    for (Tecnico tecnico : tecnicos) {
      if (tecnico.getAreaDeCobertura().cubreEstaUbicacion(ubiIncidente) && !tecnico.equals(tecnicoARemplazar)) {
        Ubicacion ubiTecnico = tecnico.getAreaDeCobertura().getUbicacion();
        if (tecnicoMasCercano == null ||
          ubiTecnico.distanciaA(ubiIncidente) < tecnicoMasCercano.getAreaDeCobertura().getUbicacion().distanciaA(ubiIncidente))
          tecnicoMasCercano = tecnico;
      }
    }

    if (tecnicoMasCercano == null) {
      Logger.info("Se quizo remplazar a un tecnico pero no se encuentra a otro, asi que se le vuelve a asignar y que se joda");
      tecnicoMasCercano = tecnicoARemplazar;
    }
    return tecnicoMasCercano;
  }

  private void setModel(HashMap<String, Object> model, Context ctx) {
    model.put("esTecnico", true);
    model.put("username", ctx.sessionAttribute("user_name"));
    model.put("user_type", ctx.sessionAttribute("user_type"));
  }
}
