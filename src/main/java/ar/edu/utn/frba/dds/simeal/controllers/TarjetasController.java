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
import io.javalin.http.Context;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TarjetasController {
  private PersonaVulnerableController personaVulnerableController;
  private TarjetaPersonaVulnerableRepository repository;
  private TarjetaColaboradorRepository tarjetaColaboradorRepository;

  public TarjetasController(PersonaVulnerableController personaVulnerableController, TarjetaPersonaVulnerableRepository repository,
                            TarjetaColaboradorRepository tarjetaColaboradorRepository) {
    this.personaVulnerableController = personaVulnerableController;
    this.repository = repository;
    this.tarjetaColaboradorRepository = tarjetaColaboradorRepository;
  }

  public void index(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    model.put("username", app.sessionAttribute("username"));
    setNavBar(model,app);
    setTarjetaPersonal(model, app);
    setTarjetasPersonasVulnerables(model, app);

    app.render("tarjetas/tarjetas_entregadas.hbs", model);
  }

  public void indexNewTarjeta(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Crear nueva tarjeta");
    setNavBar(model, app);
    setTarjetaPersonal(model, app);

    app.render("tarjetas/agregar_tarjeta.hbs", model);
  }

  public void indexBorrarTarjeta(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model, app);
    setTarjetasPersonasVulnerables(model, app);
    setTarjetaPersonal(model, app);
    app.render("tarjetas/borrar_tarjeta.hbs", model);
  }

  public void indexUpdateTarjeta(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model, app);
    setTarjetasPersonasVulnerables(model, app);
    setTarjetaPersonal(model, app);
    app.render("tarjetas/modificar_tarjeta.hbs", model);
  }

  public void delete(Context app) {
    TarjetaPersonaVulnerable tarjeta = repository.getPorNumero(app.pathParam("numeroTarjeta"));
    repository.desactivar(tarjeta);
    personaVulnerableController.delete(tarjeta.getPersonaVulnerable());

    app.redirect("/tarjeta");
  }

  public void update(Context app) {
    String newName = app.formParam("newNombre");
    String newApellido = app.formParam("newApellido");
    String newDni = app.formParam("newDni");
    String newEdad = app.formParam("newEdad");
    String numeroTarjeta = app.pathParam("numeroTarjeta");
//
    TarjetaPersonaVulnerable tarjeta = repository.getPorNumero(numeroTarjeta);
    PersonaVulnerable personaVulnerable = tarjeta.getPersonaVulnerable();
    personaVulnerableController.update(personaVulnerable, newName, newApellido, newDni, newEdad);
    app.redirect("/tarjeta");
  }


  public void create(PersonaVulnerable personaVulnerable, Context app) {
    TarjetaPersonaVulnerable tarjetaPersonaVulnerable = new TarjetaPersonaVulnerable(
      GeneradorNrosTarjeta.generarCodigo(),
      personaVulnerable
    );

    repository.guardar(tarjetaPersonaVulnerable);
    ServiceLocator.getController(AltaPersonaVulnerableController.class).create(tarjetaPersonaVulnerable, personaVulnerable, app);
  }


  private void setNavBar(HashMap<String, Object> model, Context ctx) {
    model.put("tarjetas", "seleccionado");
    model.put("user_type", ctx.sessionAttribute("user_type").toString().toLowerCase());
    if (ctx.sessionAttribute("user_type") == "HUMANO")
      model.put("esHumano","true");

    model.put("username", ctx.sessionAttribute("username"));
  }

  private void setTarjetaPersonal(HashMap<String, Object> model, Context ctx) {
    TarjetaColaborador tarjetaColaborador = tarjetaColaboradorRepository
      .getPorColaborador(ctx.sessionAttribute("colaborador_id"));
    if (tarjetaColaborador == null) {
      throw new RuntimeException("No habia tarjetas de colaborador con id = 1");
    }

    String codigo =String.format("%06d", tarjetaColaborador.getId());
    model.put("numeroTarjetaPersonal",codigo.substring(0,3) + "." + codigo.substring(3));
    model.put("tarjeta_personal_id", tarjetaColaborador.getId());
    SolicitudOperacionRepository solicitudOperacionRepository =
      (SolicitudOperacionRepository) ServiceLocator.getRepository(SolicitudOperacionRepository.class);
    List<SolicitudOperacionHeladera> solicitudes = solicitudOperacionRepository.getPorTarjetaColaborador(tarjetaColaborador.getId());
    setSolicitudes(model,solicitudes);
  }

  private void setSolicitudes(HashMap<String, Object> model, List<SolicitudOperacionHeladera> solicitudes) {
    List<SolicitudOperacionDTO> solicitudesDTOs = new ArrayList<>();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM  HH:mm:ss");
    for (SolicitudOperacionHeladera solicitud : solicitudes) {
      solicitudesDTOs.add(
        SolicitudOperacionDTO.builder()
          .nombre(solicitud.getHeladera().getNombre())
          .ubicacion(solicitud.getHeladera().getUbicacion().getStringUbi())
          .horaFin(String.valueOf(dateTimeFormatter.format(solicitud.getHoraInicio().plusHours(solicitud.getHorasParaEjecutarse()))))
          .build()
      );
    }
    model.put("solicitudes", solicitudesDTOs);
  }

  private void setTarjetasPersonasVulnerables(HashMap<String, Object> model, Context app) {
    ColaboracionRepository repository = (ColaboracionRepository) ServiceLocator.getRepository(ColaboracionRepository.class);
    List<DarDeAltaPersonaVulnerable> personas = (List<DarDeAltaPersonaVulnerable>) repository
      .getPorColaborador((app.sessionAttribute("colaborador_id")), DarDeAltaPersonaVulnerable.class);

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
