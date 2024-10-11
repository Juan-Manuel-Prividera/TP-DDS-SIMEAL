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
    setNavBar(model);
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


  public void create(PersonaVulnerable personaVulnerable) {
    TarjetaPersonaVulnerable tarjetaPersonaVulnerable = new TarjetaPersonaVulnerable(
      GeneradorNrosTarjeta.generarCodigo(),
      personaVulnerable
    );

    repository.guardar(tarjetaPersonaVulnerable);
    ServiceLocator.getController(AltaPersonaVulnerableController.class).create(tarjetaPersonaVulnerable, personaVulnerable);
  }


  private void setNavBar(HashMap<String, Object> model) {
    model.put("tarjetas", "seleccionado");
    model.put("esHumano","true");
    model.put("user_type", "humano");
  }

  // TODO: Poner los datos del usuario que haga la request
  private void setTarjetaPersonal(HashMap<String, Object> model) {
    //TODO: Sacar ID de la sesion
    TarjetaColaborador tarjetaColaborador = tarjetaColaboradorRepository.getPorColaborador(1L);
    if (tarjetaColaborador == null) {
      throw new RuntimeException("No habia tarjetas de colaborador con id = 1");
    }

    String codigo =String.format("%06d", tarjetaColaborador.getId());
    model.put("numeroTarjetaPersonal",codigo.substring(0,3) + "." + codigo.substring(3));

    SolicitudOperacionRepository solicitudOperacionRepository =
      (SolicitudOperacionRepository) ServiceLocator.getRepository(SolicitudOperacionRepository.class);
    List<SolicitudOperacionHeladera> solicitudes = solicitudOperacionRepository.getPorTarjetaColaborador(tarjetaColaborador.getId());
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
