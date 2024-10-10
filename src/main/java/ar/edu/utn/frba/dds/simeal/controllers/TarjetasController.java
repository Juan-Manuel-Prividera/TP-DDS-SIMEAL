package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.controllers.colaboraciones.AltaPersonaVulnerableController;
import ar.edu.utn.frba.dds.simeal.models.dtos.SolicitudOperacionDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.TarjetaPersonaVulnerableDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DarDeAltaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.SolicitudOperacionHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.repositories.*;
import ar.edu.utn.frba.dds.simeal.utils.GeneradorNrosTarjeta;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.logger.LoggerType;
import io.javalin.http.Context;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TarjetasController {
  Logger logger = Logger.getInstance("tarjetas.log");;

  public void index(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    model.put("username", app.sessionAttribute("username"));
    logger.log(LoggerType.DEBUG, "Entrando a set navbar");
    setNavBar(model);
    logger.log(LoggerType.DEBUG, "Entrando a set tarjeta personal");
    setTarjetaPersonal(model);
    setTarjetasPersonasVulnerables(model);

    app.render("tarjetas/tarjetas_entregadas.hbs", model);
  }

  public void indexNewTarjeta(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Crear nueva tarjeta");
    model.put("username", app.sessionAttribute("username"));
    setNavBar(model);
    setTarjetaPersonal(model);

    app.render("tarjetas/agregar_tarjeta.hbs", model);
  }

  public void indexBorrarTarjeta(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model);
    setTarjetasPersonasVulnerables(model);
    setTarjetaPersonal(model);
    app.render("tarjetas/borrar_tarjeta.hbs", model);
  }

  public void indexUpdateTarjeta(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model);
    setTarjetasPersonasVulnerables(model);
    setTarjetaPersonal(model);
    app.render("tarjetas/modificar_tarjeta.hbs", model);
  }

  public void delete(Context app) {
    TarjetaPersonaVulnerableRepository repositorio = (TarjetaPersonaVulnerableRepository) ServiceLocator.getRepository(TarjetaPersonaVulnerableRepository.class);
    TarjetaPersonaVulnerable tarjeta = repositorio.getPorNumero(app.pathParam("numeroTarjeta"));

    // TODO: Hay que borrar tambien a la persona vulnerable o solo la tarjeta?
    repositorio.desactivar(tarjeta);
    ServiceLocator.getController(PersonaVulnerableController.class).delete(tarjeta.getPersonaVulnerable());

    app.redirect("/tarjeta");
  }

  public void update(Context app) {
    String newName = app.formParam("newNombre");
    String newApellido = app.formParam("newApellido");
    String newDni = app.formParam("newDni");
    String newEdad = app.formParam("newEdad");
    String numeroTarjeta = app.pathParam("numeroTarjeta");
    TarjetaPersonaVulnerableRepository repo =
      (TarjetaPersonaVulnerableRepository) ServiceLocator.getRepository(TarjetaPersonaVulnerableRepository.class);

    TarjetaPersonaVulnerable tarjeta = repo.getPorNumero(numeroTarjeta);
    PersonaVulnerable personaVulnerable = tarjeta.getPersonaVulnerable();
    ServiceLocator.getController(PersonaVulnerableController.class).update(personaVulnerable, newName, newApellido, newDni, newEdad);

    app.redirect("/tarjeta");
  }


  public void create(PersonaVulnerable personaVulnerable) {
    TarjetaPersonaVulnerable tarjetaPersonaVulnerable = new TarjetaPersonaVulnerable(
      GeneradorNrosTarjeta.generarCodigo(),
      personaVulnerable
    );

    ServiceLocator.getRepository(Repositorio.class).guardar(tarjetaPersonaVulnerable);
    ServiceLocator.getController(AltaPersonaVulnerableController.class).create(tarjetaPersonaVulnerable, personaVulnerable);
  }


  private void setNavBar(HashMap<String, Object> model) {
    model.put("tarjetas", "seleccionado");
  }

  // TODO: Poner los datos del usuario que haga la request
  private void setTarjetaPersonal(HashMap<String, Object> model) {
    TarjetaColaboradorRepository repository = (TarjetaColaboradorRepository) ServiceLocator.getRepository(TarjetaColaboradorRepository.class);
    //TODO: Sacar ID de la sesion
    TarjetaColaborador tarjetaColaborador = repository.getPorColaborador(1L);
    if (tarjetaColaborador == null) {
      throw new RuntimeException("No habia tarjetas de colaborador con id = 1");
    }
    logger.log(LoggerType.DEBUG, "Se obtuvo la tarjeta de ID = " + tarjetaColaborador.getId());

    String codigo =String.format("%06d", tarjetaColaborador.getId());
    model.put("numeroTarjetaPersonal",codigo.substring(0,3) + "." + codigo.substring(3));

    SolicitudOperacionRepository solicitudOperacionRepository = (SolicitudOperacionRepository) ServiceLocator.getRepository(SolicitudOperacionRepository.class);
    List<SolicitudOperacionHeladera> solicitudes = solicitudOperacionRepository.getPorTarjetaColaborador(tarjetaColaborador.getId());
    logger.log(LoggerType.INFORMATION,"Hay al menos una solicitud en la lista de solicitudes: " + solicitudes.get(0).getId());
    setSolicitudes(model,solicitudes);
  }

  private void setSolicitudes(HashMap<String, Object> model, List<SolicitudOperacionHeladera> solicitudes) {
    List<SolicitudOperacionDTO> solicitudesDTOs = new ArrayList<>();
    for (SolicitudOperacionHeladera solicitud : solicitudes) {
      solicitudesDTOs.add(
        SolicitudOperacionDTO.builder()
          .nombre(solicitud.getHeladera().getNombre())
          .ubicacion(solicitud.getHeladera().getUbicacion().getStringUbi())
          .horaFin(LocalTime.from(LocalTime.of(solicitud.getHoraSolicitud().getHour() + solicitud.getHorasParaEjecutarse(),solicitud.getHoraSolicitud().getMinute())).toString())
          .build()
      );
    }
    logger.log(LoggerType.DEBUG, "Se creo la lista de solicitudes: " + solicitudesDTOs.size());
    model.put("solicitudes", solicitudesDTOs);
  }

  private void setTarjetasPersonasVulnerables(HashMap<String, Object> model) {
    ColaboracionRepository repository = (ColaboracionRepository) ServiceLocator.getRepository(ColaboracionRepository.class);
    // TODO: Sacar id de la sesion
    List<DarDeAltaPersonaVulnerable> personas = (List<DarDeAltaPersonaVulnerable>) repository
      .getPorColaborador(1L, DarDeAltaPersonaVulnerable.class);

    List<TarjetaPersonaVulnerableDTO> tarjetaPersonaVulnerableDTOS = new ArrayList<>();

    for (DarDeAltaPersonaVulnerable persona : personas) {
      if (persona.getTarjeta().getActivo()) {
        tarjetaPersonaVulnerableDTOS.add(TarjetaPersonaVulnerableDTO.builder()
          .numero(persona.getTarjeta().getCodigo())
          .dniPropietario(persona.getPersonaVulnerable().getDocumento().getNroDocumento())
          .edadPropietario(String.valueOf(persona.getPersonaVulnerable().getEdad()))
          .nombrePropietario(persona.getPersonaVulnerable().getNombre())
          .apellidoPropietario(persona.getPersonaVulnerable().getApellido())
          .usosDisponibles(String.valueOf(persona.getTarjeta().usosRestantes()))
          .build());
      }
    }

    model.put("tarjetasEntregadas", tarjetaPersonaVulnerableDTOS);
  }
}
