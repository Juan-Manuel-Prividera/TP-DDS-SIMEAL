package ar.edu.utn.frba.dds.simeal.controllers.tecnico;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.EncargoTecnico;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Tecnico;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.EncargoTecnicoRepostiry;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Notificador;
import io.javalin.http.Context;

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
      .aceptado(false) // Por defecto no esta aceptado y luego el tecnico puede aceptarlo manualmente
      .build();

    repoEncargo.guardar(encargoTecnico);
  }
  // GET /encargo/{encargo_id}/aceptado
  // El tecnico selecciona un encargo y lo acepta
  public void aceptarEncargo(Context ctx) {
    EncargoTecnico encargoTecnico = (EncargoTecnico) repoEncargo
      .buscarPorId(Long.valueOf(ctx.pathParam("encargo_id")),EncargoTecnico.class);

    encargoTecnico.setAceptado(true);
    repoEncargo.actualizar(encargoTecnico);
  }

  // GET /encargo/{encargo_id}/rechazado
  public void rechazarEncargo(Context ctx) {
    EncargoTecnico encargoTecnico = (EncargoTecnico) repoEncargo
      .buscarPorId(Long.valueOf(ctx.pathParam("encargo_id")),EncargoTecnico.class);

    encargoTecnico.setAceptado(false);
    repoEncargo.actualizar(encargoTecnico);

    // Como el tecnico mas cercano rechazo, hay que avisar al segundo mas cercano
    Tecnico tecnicoNuevo = remplazarTecnico(encargoTecnico.getTecnico(),encargoTecnico.getIncidente());
    Mensaje mensaje = new Mensaje(
      encargoTecnico.getIncidente().getNotificacion(),
      "Aviso de Incidente en " + encargoTecnico.getIncidente().getHeladera().getUbicacion().getStringUbi() +
        "\n Podra ver el detalle del aviso en el apartado de encargos e informar si lo acepta o rechaza.");
    Notificador.notificar(tecnicoNuevo,mensaje);
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
    return tecnicoMasCercano;
  }
}
