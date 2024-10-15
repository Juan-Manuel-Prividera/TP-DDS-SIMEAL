package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonaVulnerableController {

  public void create(Context app) {
    PersonaVulnerable personaVulnerable;
    List<PersonaVulnerable> personasVulnerable = new ArrayList<>();

    personaVulnerable = PersonaVulnerable.builder()
      .nombre(app.formParam("nombre"))
      .apellido(app.formParam("apellido"))
      .documento(new Documento(TipoDocumento.valueOf(app.formParam("documentoTipo").toUpperCase()), app.formParam("documentoNro")))
      .fechaNacimiento(LocalDate.parse(app.formParam("nacimiento")))
      .build();
    personaVulnerable.calcularEdad();

    if (!app.formParams("nombreHijo").get(0).isEmpty()) {
      List<String> nombresHijos = app.formParams("nombreHijo");
      List<String> apellidosHijos = app.formParams("apellidoHijo");
      List<String> documentoTIpoHijos = app.formParams("documentoTipoHijo");
      List<String> documentoNroHijos = app.formParams("documentoNroHijo");
      List<String> nacimientoHijos = app.formParams("nacimientoHijo");

      for (int i = 0; i < nombresHijos.size(); i++) {
        personasVulnerable.add(
          PersonaVulnerable.builder()
            .nombre(nombresHijos.get(i))
            .apellido(apellidosHijos.get(i))
            .documento(new Documento(TipoDocumento.valueOf(documentoTIpoHijos.get(i).toUpperCase()), documentoNroHijos.get(i)))
            .fechaNacimiento(LocalDate.parse(nacimientoHijos.get(i)))
            .build()
        );
      }
      personaVulnerable.setHijos(personasVulnerable);
    }

    ServiceLocator.getRepository(Repositorio.class).guardar(personaVulnerable);
    ServiceLocator.getController(TarjetasController.class).create(personaVulnerable, app);
    app.redirect("/tarjeta");
  }

  public void delete(PersonaVulnerable personaVulnerable) {
    ServiceLocator.getRepository(Repositorio.class).desactivar(personaVulnerable);
  }

  public void update(PersonaVulnerable personaVulnerable, String newName, String newApellido, String newDni, String newEdad) {
    if (!Objects.equals(newName, personaVulnerable.getNombre())) {
      personaVulnerable.setNombre(newName);
    }
    if (!Objects.equals(newApellido, personaVulnerable.getApellido())) {
      personaVulnerable.setApellido(newApellido);
    }
    if (!Objects.equals(newDni, personaVulnerable.getDocumento().getNroDocumento())) {
      personaVulnerable.getDocumento().setNroDocumento(newDni);
    }
    if (!Objects.equals(Integer.parseInt(newEdad), personaVulnerable.getEdad())) {
      personaVulnerable.setEdad(Integer.parseInt(newEdad));
    }

    ServiceLocator.getRepository(Repositorio.class).actualizar(personaVulnerable);
  }
}
