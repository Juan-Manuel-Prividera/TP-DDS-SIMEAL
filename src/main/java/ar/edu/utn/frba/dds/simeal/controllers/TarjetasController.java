package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.dtos.SolicitudOperacionDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.TarjetaPersonaVulnerableDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DarDeAltaPersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.SolicitudOperacionHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.SolicitudOperacionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.TarjetaColaboradorRepository;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TarjetasController {

  public void index(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model);
    setTarjetaPersonal(model);
    setTarjetasPersonasVulnerables(model);

    app.render("tarjetas/home.hbs", model);
  }

  private void setNavBar(HashMap<String, Object> model) {
    model.put("username", "preguntarleAEliDeDondeLoSaco :)");
    model.put("tarjetas", "seleccionado");
    model.put("colaboraciones", "");
    model.put("ofertas", "");
    model.put("heladeras", "");
  }

  private void setTarjetaPersonal(HashMap<String, Object> model) {
    TarjetaColaboradorRepository repository = (TarjetaColaboradorRepository) ServiceLocator.getRepository(TarjetaColaboradorRepository.class);
    // TODO: Ver de donde sacar la data del usario que hace las request
    TarjetaColaborador tarjetaColaborador = repository.getPorColaborador(1L);
    // TODO: Ver como hacer que se muestre el ID mas lindo onda xxx.xxx.xxx en vez de 4
    model.put("numeroTarjetaPersonal", tarjetaColaborador.getId());

    SolicitudOperacionRepository solicitudOperacionRepository = (SolicitudOperacionRepository) ServiceLocator.getRepository(SolicitudOperacionRepository.class);
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
          .horaFin(solicitud.getHoraSolicitud().toString())
          .build()
      );
    }
    model.put("solicitudes", solicitudesDTOs);
  }

  private void setTarjetasPersonasVulnerables(HashMap<String, Object> model) {
    ColaboracionRepository repository = (ColaboracionRepository) ServiceLocator.getRepository(ColaboracionRepository.class);
    List<DarDeAltaPersonaVulnerable> personas = (List<DarDeAltaPersonaVulnerable>) repository.getPorColaborador(1L, DarDeAltaPersonaVulnerable.class);
    List<TarjetaPersonaVulnerableDTO> tarjetaPersonaVulnerableDTOS = new ArrayList<>();

    for (DarDeAltaPersonaVulnerable persona : personas) {
      tarjetaPersonaVulnerableDTOS.add(TarjetaPersonaVulnerableDTO.builder()
          .numero(persona.getTarjeta().getCodigo())
          .dniPropietario(persona.getPersonaVulnerable().getDocumento().getNroDocumento())
          .edadPropietario(String.valueOf(persona.getPersonaVulnerable().getEdad()))
          .nombrePropietario(persona.getPersonaVulnerable().getNombre())
          .usosDisponibles(String.valueOf(persona.getTarjeta().calcularLimiteDeUso() - persona.getTarjeta().getUsosHechos()))
          .build());
    }

    model.put("tarjetasEntregadas", tarjetaPersonaVulnerableDTOS);
  }

  public void indexNewTarjeta(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    model.put("titulo", "Crear nueva tarjeta");
    setNavBar(model);
    setTarjetaPersonal(model);

    app.render("tarjetas/agregar_tarjeta.hbs", model);
  }
  public void indexBorrarTarjeta(Context app) {

  }
  public void indexUpdateTarjeta(Context app) {

  }



}
