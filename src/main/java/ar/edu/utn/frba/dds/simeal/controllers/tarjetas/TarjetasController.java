package ar.edu.utn.frba.dds.simeal.controllers.tarjetas;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.controllers.colaboraciones.AltaPersonaVulnerableController;
import ar.edu.utn.frba.dds.simeal.models.dtos.SolicitudOperacionDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.TarjetaPersonaVulnerableDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DarDeAltaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.SolicitudOperacionHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.SolicitudOperacionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.TarjetaColaboradorRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.TarjetaPersonaVulnerableRepository;
import ar.edu.utn.frba.dds.simeal.utils.GeneradorNrosTarjeta;
import ar.edu.utn.frba.dds.simeal.utils.ValidadorDeInputs;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TarjetasController {
  private final PersonaVulnerableController personaVulnerableController;
  private final TarjetaPersonaVulnerableRepository repository;
  private final TarjetaColaboradorRepository tarjetaColaboradorRepository;
  private final ColaboracionRepository colaboracionRepository;

  public TarjetasController(PersonaVulnerableController personaVulnerableController, TarjetaPersonaVulnerableRepository repository,
                            TarjetaColaboradorRepository tarjetaColaboradorRepository, ColaboracionRepository colaboracionRepository) {
    this.personaVulnerableController = personaVulnerableController;
    this.repository = repository;
    this.colaboracionRepository = colaboracionRepository;
    this.tarjetaColaboradorRepository = tarjetaColaboradorRepository;
  }

  // GET /tarjeta
  public void index(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model,app);
    setTarjetaPersonal(model, app);
    setTarjetasPersonasVulnerables(model, app);

    // Invalidamos cache de navegador para que no mueste datos desactualizados :|
    invalidarCacheNavegador(app);

    app.render("tarjetas/tarjetas_entregadas.hbs", model);
  }
  // GET /tarjeta/new
  public void indexNewTarjeta(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Crear nueva tarjeta");
    setNavBar(model, app);
    setTarjetaPersonal(model, app);
    invalidarCacheNavegador(app);

    String failed = app.queryParam("failed");
    String action = app.queryParam("action");

    if (Objects.equals(failed,"false") && Objects.equals(action,"createPersona")){
      model.put("popup_title", "Tarjeta y Persona creadas correctamente");
      model.put("popup_ruta", "/tarjeta");
    } else if (Objects.equals(failed,"true") && Objects.equals(action,"createPersona")){
      model.put("popup_title", "Error al crear la tarjeta y/o persona");
      model.put("popup_message", "Por favor revise los campos del formulario y vuelva a intentarlo");
      model.put("popup_ruta", "/tarjeta/new");
    }
    app.render("tarjetas/agregar_tarjeta.hbs", model);
  }
  // GET /tarjeta/delete
  public void indexBorrarTarjeta(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model, app);
    setTarjetasPersonasVulnerables(model, app);
    setTarjetaPersonal(model, app);
    invalidarCacheNavegador(app);
    String failed = app.queryParam("failed");
    String action = app.queryParam("action");

    if (Objects.equals(failed,"false") && Objects.equals(action,"deletePersona")){
      model.put("popup_title", "Tarjeta y Persona borradas correctamente");
    } else if (Objects.equals(failed,"true") && Objects.equals(action,"deletePersona")){
      model.put("popup_title", "Error al borrar la tarjeta y/o persona");
    }
    model.put("popup_ruta", "/tarjeta/delete");
    app.render("tarjetas/borrar_tarjeta.hbs", model);
  }
  // GET /tarjeta/update
  public void indexUpdateTarjeta(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model, app);
    setTarjetasPersonasVulnerables(model, app);
    setTarjetaPersonal(model, app);
    invalidarCacheNavegador(app);

    String failed = app.queryParam("failed");
    String action = app.queryParam("action");

    if (Objects.equals(failed,"false") && Objects.equals(action,"updatePersona")){
      model.put("popup_title", "Tarjeta y Persona actualizadas correctamente");
    } else if (Objects.equals(failed,"true") && Objects.equals(action,"updatePersona")){
      model.put("popup_title", "Error al actualizar la tarjeta y/o persona");
      model.put("popup_message", "Verifique que los datos ingresados sean validos");
    }

    model.put("popup_ruta", "/tarjeta/update");
    app.render("tarjetas/modificar_tarjeta.hbs", model);
  }
  // POST /tarjeta/delete/{numeroTarjeta}
  public void delete(Context app) {
    try {
      TarjetaPersonaVulnerable tarjeta = repository.getPorNumero(app.pathParam("numeroTarjeta"));
      repository.desactivar(tarjeta);
      personaVulnerableController.delete(tarjeta.getPersonaVulnerable());
      repository.refresh(tarjeta);
      app.redirect("/tarjeta/delete?failed=false&action=deletePersona");
    } catch (Exception e) {
      app.redirect("/tarjeta/delete?failed=true&action=deletePersona");
    }
  }

  // POST /tarjeta/update/{numeroTarjeta}
  public void update(Context app) {
    try {
      String newName = app.formParam("newNombre");
      String newApellido = app.formParam("newApellido");
      String newDni = app.formParam("newDni");
      String newEdad = app.formParam("newEdad");
      String numeroTarjeta = app.pathParam("numeroTarjeta");
      if (!validarInput(newName,newApellido,newDni,newEdad)) {
        Logger.error("Se quizo actualizar una tarjetas con parametros invalidos :0");
        throw new RuntimeException("Los parametros ingreados no son validos");
      }
      TarjetaPersonaVulnerable tarjeta = repository.getPorNumero(numeroTarjeta);
      PersonaVulnerable personaVulnerable = tarjeta.getPersonaVulnerable();
      personaVulnerableController.update(personaVulnerable, newName, newApellido, newDni, newEdad);
      repository.refresh(tarjeta);
      app.redirect("/tarjeta/update?failed=false&action=updatePersona");
    } catch (Exception e) {
      app.redirect("/tarjeta/update?failed=true&action=updatePersona");
    }
  }

  private boolean validarInput(String nombre,String apellido,String dni,String edad) {
    if (Objects.equals(dni, "")) {
      return ValidadorDeInputs.soloNumero(edad) &&
        ValidadorDeInputs.soloLetras(nombre) && ValidadorDeInputs.soloLetras(apellido);
    }
    return ValidadorDeInputs.soloNumero(dni) && ValidadorDeInputs.soloNumero(edad) &&
           ValidadorDeInputs.soloLetras(nombre) && ValidadorDeInputs.soloLetras(apellido);
  }

  // Se llama desde personaVulnerableController
  public void create(PersonaVulnerable personaVulnerable, Context app) {
    Logger.debug("Persona creada, creando tarjeta...");
    TarjetaPersonaVulnerable tarjetaPersonaVulnerable = new TarjetaPersonaVulnerable(
      GeneradorNrosTarjeta.generarCodigo(),
      personaVulnerable
    );
    Logger.debug("Se crea tarjeta con limite de uso: " + tarjetaPersonaVulnerable.calcularLimiteDeUso());
    repository.guardar(tarjetaPersonaVulnerable);
    repository.refresh(tarjetaPersonaVulnerable);
    ServiceLocator.getController(AltaPersonaVulnerableController.class).create(tarjetaPersonaVulnerable, personaVulnerable, app);
  }


  private void setNavBar(HashMap<String, Object> model, Context ctx) {
    model.put("tarjetas", "seleccionado");
    model.put("user_type", ctx.sessionAttribute("user_type").toString().toLowerCase());
    if (ctx.sessionAttribute("user_type") == "HUMANO")
      model.put("esHumano","true");

    model.put("username", ctx.sessionAttribute("user_name"));
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
          .horaFin(dateTimeFormatter.format(solicitud.getHoraDeRealizacion().plusHours(solicitud.getHorasParaEjecutarse())))
          .build()
      );
    }
    model.put("solicitudes", solicitudesDTOs);
  }

  private void setTarjetasPersonasVulnerables(HashMap<String, Object> model, Context app) {
    List<DarDeAltaPersonaVulnerable> personas = (List<DarDeAltaPersonaVulnerable>) colaboracionRepository
      .getPorColaborador((app.sessionAttribute("colaborador_id")), DarDeAltaPersonaVulnerable.class);

    List<TarjetaPersonaVulnerableDTO> tarjetaPersonaVulnerableDTOS = new ArrayList<>();

    for (DarDeAltaPersonaVulnerable persona : personas) {
      TarjetaPersonaVulnerableDTO tarjetaPersonaVulnerableDTO;
      if (persona.getTarjeta().getActivo()) {
        tarjetaPersonaVulnerableDTOS.add(TarjetaPersonaVulnerableDTO.builder()
          .numero(persona.getTarjeta().getCodigo())
        //  .dniPropietario(persona.getPersonaVulnerable().getDocumento().getNroDocumento())
          .edadPropietario(String.valueOf(persona.getPersonaVulnerable().getEdad()))
          .nombrePropietario(persona.getPersonaVulnerable().getNombre())
          .apellidoPropietario(persona.getPersonaVulnerable().getApellido())
          .usosDisponibles(String.valueOf(persona.getTarjeta().usosRestantes()))
          .build());
      }
    }

    model.put("tarjetasEntregadas", tarjetaPersonaVulnerableDTOS);
  }

  private void invalidarCacheNavegador(Context app) {
    app.header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    app.header("Pragma", "no-cache");
    app.header("Expires", "0");
  }
}
