package ar.edu.utn.frba.dds.simeal.controllers.tecnico;


import ar.edu.utn.frba.dds.simeal.models.creacionales.MedioDeContactoFactory;
import ar.edu.utn.frba.dds.simeal.models.dtos.TecnicoDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Tecnico;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.AreaDeCobertura;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Provincia;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.EncargoTecnicoRepostiry;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TecnicoController {
  private Repositorio repositorio;
  private EncargoTecnicoRepostiry encargoTecnicoRepostiry;

  public TecnicoController(Repositorio repositorio, EncargoTecnicoRepostiry encargoTecnicoRepostiry) {
    this.repositorio = repositorio;
    this.encargoTecnicoRepostiry = encargoTecnicoRepostiry;
  }

  // Esto esta en paginas del admin para registrar tecnicos
  // GET /tecnico
  public void index(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setModel(model,ctx);
    setTecnicos(model);
    ctx.render("admin/tecnicos.hbs", model);
  }
  // GET /registro/tecnico
  public void registroIndex(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setModel(model,ctx);
    ctx.render("admin/registro_tecnicos.hbs", model);
  }
  // POST /tecnico
  public void crearTecnico(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setModel(model,ctx);
    try {
      Contacto contacto = new Contacto(
        ctx.formParam("infoContacto"),
        MedioDeContactoFactory.crearMedioDeContacto(ctx.formParam("medioContacto")));
      Tecnico tecnico = Tecnico.builder()
        .nombre(ctx.formParam("nombre"))
        .apellido(ctx.formParam("apellido"))
        .cuil(ctx.formParam("cuil"))
        .documento(new Documento(
          TipoDocumento.valueOf(ctx.formParam("tipoDocumento")),
          ctx.formParam("numeroDocumento")
        ))
        .contactoPreferido(contacto)
        .areaDeCobertura(new AreaDeCobertura(
          new Ubicacion(
            ctx.formParam("calleCobertura"),
            Integer.parseInt(ctx.formParam("alturaCobertura")),
            Provincia.valueOf(ctx.formParam("provincia").replace("+", "_").replace(" ", "_")),
            Integer.parseInt(ctx.formParam("codigo_postal"))),
            Double.parseDouble(ctx.formParam("radioCobertura")
          )
        ))
        .contactos(List.of(contacto))
        .build();

      repositorio.guardar(tecnico);
      ctx.redirect("/tecnico", HttpStatus.CREATED);
      Logger.debug("Tecnico creado correctamente");
    } catch (Exception e) {
      ctx.status(500);
      Logger.error("Ocurrio un error en la creacion del tecnico: " + e.getMessage());
    }
  }
  // DELETE /tecnico/{tecnico_id}
  public void borrarTecnico(Context ctx) {
    try {
      Tecnico tecnico = (Tecnico) repositorio
        .buscarPorId(Long.valueOf(ctx.pathParam("tecnico_id")), Tecnico.class);

      repositorio.desactivar(tecnico);
      repositorio.refresh(tecnico);
      ctx.redirect("tecnico", HttpStatus.OK);
    } catch (Exception e) {
      ctx.status(500);
    }
  }

  private void setModel(HashMap<String, Object> model, Context ctx) {
    model.put("tecnico", "seleccionado");
    model.put("titulo", "Técnicos");
    model.put("user_type", ctx.sessionAttribute("user_type"));
    model.put("username", ctx.sessionAttribute("user_name"));

    if (ctx.sessionAttribute("user_type") == "admin") {
      model.put("esAdmin", true);
    }
  }

  private void setTecnicos(HashMap<String,Object> model) {
    List<Tecnico> tecnicos = (List<Tecnico>) repositorio.obtenerTodos(Tecnico.class);
    List<TecnicoDTO> tecnicosDTO = new ArrayList<>();
    for(Tecnico tecnico : tecnicos) {
      tecnicosDTO.add(new TecnicoDTO(tecnico));
    }
    model.put("tecnicos", tecnicosDTO);
  }


}

