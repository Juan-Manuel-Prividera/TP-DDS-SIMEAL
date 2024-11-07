package ar.edu.utn.frba.dds.simeal.controllers.tarjetas;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Provincia;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.Permiso;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonaVulnerableController {
  // POST /tarjeta/new
  public void create(Context app) {
    try {
      PersonaVulnerable personaVulnerable;
      List<PersonaVulnerable> personasVulnerable = new ArrayList<>();

      personaVulnerable = PersonaVulnerable.builder()
        .nombre(app.formParam("nombre"))
        .apellido(app.formParam("apellido"))
        .fechaNacimiento(LocalDate.parse(app.formParam("nacimiento")))
        .build();

      if (app.formParam("documentoNro") != null && app.formParam("documentoTipo") != null) {
        personaVulnerable.setDocumento(
          new Documento(TipoDocumento.valueOf(app.formParam("documentoTipo").toUpperCase()), app.formParam("documentoNro")));
      }

      // Puede tener o no domicilio por consigna
      if (app.formParam("calle_nombre") != null) {
        personaVulnerable.setDomilicio(new Ubicacion(
          app.formParam("calle_nombre"), Integer.parseInt(app.formParam("calle_altura")),
          Provincia.valueOf(app.formParam("provincia")), Integer.parseInt(app.formParam("codigo_postal"))));
      }

      personaVulnerable.calcularEdad();
      // Asumo que los hijos tienen el mismo docmicilio ya que son menores...
      if (!app.formParams("nombreHijo").get(0).isEmpty()) {
        List<String> nombresHijos = app.formParams("nombreHijo");
        List<String> apellidosHijos = app.formParams("apellidoHijo");
        List<String> documentoTIpoHijos = app.formParams("documentoTipoHijo");
        List<String> documentoNroHijos = app.formParams("documentoNroHijo");
        List<String> nacimientoHijos = app.formParams("nacimientoHijo");
        Logger.debug("Cantidad de hijos: "+ nombresHijos.size());
        for (int i = 0; i < nombresHijos.size(); i++) {
          PersonaVulnerable persona =
            PersonaVulnerable.builder()
              .nombre(nombresHijos.get(i))
              .apellido(apellidosHijos.get(i))
              .fechaNacimiento(LocalDate.parse(nacimientoHijos.get(i)))
              .build();

          // La persona puede o no tene documento... por consigna
          if (documentoNroHijos.get(i) != null && documentoTIpoHijos.get(i) != null) {
              persona.setDocumento((new Documento(TipoDocumento.valueOf(documentoTIpoHijos.get(i).toUpperCase()), documentoNroHijos.get(i))));
          }
        }

        personaVulnerable.setHijos(personasVulnerable);
      }

      ServiceLocator.getRepository(Repositorio.class).guardar(personaVulnerable);
      ServiceLocator.getRepository(Repositorio.class).refresh(personaVulnerable);
      ServiceLocator.getController(TarjetasController.class).create(personaVulnerable, app);

      app.redirect("/tarjeta/new?failed=false&action=createPersona");
    } catch (Exception e) {
      app.redirect("/tarjeta/new?failed=true&action=createPersona");
    }
  }

  public void delete(PersonaVulnerable personaVulnerable) {
    ServiceLocator.getRepository(Repositorio.class).desactivar(personaVulnerable);
    ServiceLocator.getRepository(Repositorio.class).refresh(personaVulnerable);
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
    ServiceLocator.getRepository(Repositorio.class).refresh(personaVulnerable);
  }
}
