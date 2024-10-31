package ar.edu.utn.frba.dds.simeal.controllers.tecnico;


import ar.edu.utn.frba.dds.simeal.controllers.UsuariosController;
import ar.edu.utn.frba.dds.simeal.models.creacionales.MedioDeContactoFactory;
import ar.edu.utn.frba.dds.simeal.models.dtos.TecnicoDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Tecnico;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.MedioContacto;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.WhatsApp;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.AreaDeCobertura;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Provincia;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.EncargoTecnicoRepostiry;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TecnicoController {
  private Repositorio repositorio;
  private EncargoTecnicoRepostiry encargoTecnicoRepostiry;
  private UsuariosController usuariosController;

  public TecnicoController(Repositorio repositorio, EncargoTecnicoRepostiry encargoTecnicoRepostiry, UsuariosController usuariosController) {
    this.repositorio = repositorio;
    this.encargoTecnicoRepostiry = encargoTecnicoRepostiry;
    this.usuariosController = usuariosController;
  }

  // Esto esta en paginas del admin para registrar tecnicos
  // GET /tecnico
  public void index(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setModel(model,ctx);
    setTecnicos(model);

    Logger.debug("exito param: " + ctx.queryParam("exito"));
    Logger.debug("action param: " + ctx.queryParam("action"));

    if (ctx.queryParam("exito") == null) {
      Logger.debug("No entra a popup");
      ctx.render("admin/tecnicos.hbs", model);

    } else if (Objects.equals(ctx.queryParam("exito"), "true") && Objects.equals(ctx.queryParam("action"), "delete")) {
      Logger.debug("Entra a popup");
      model.put("popup_title", "Tecnico eliminado");
      model.put("popup_message", "El tecnico se elimino correctamente");
      model.put("popup_ruta", "/tecnico");
      ctx.render("admin/tecnicos.hbs", model);

    } else if (Objects.equals(ctx.queryParam("exito"), "false") && Objects.equals(ctx.queryParam("action"), "delete")) {
      Logger.debug("Entra a popup");
      model.put("popup_title", "Error en la eliminación");
      model.put("popup_message", "Ocurrio un error al eliminar al tecnico");
      model.put("popup_ruta", "/tecnico");
      ctx.render("admin/tecnicos.hbs", model);

    }
  }

  // GET /registro/tecnico
  public void registroIndex(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setModel(model,ctx);
    if (Objects.equals(ctx.queryParam("exito"), "true") && Objects.equals(ctx.queryParam("action"), "create")) {
      model.put("popup_title", "Tecnico creado");
      model.put("popup_message", "El tecnico se creo correctamente");
      model.put("popup_ruta", "/tecnico");
      ctx.render("admin/registro_tecnicos.hbs", model);

    } else if (Objects.equals(ctx.queryParam("exito"), "false") && Objects.equals(ctx.queryParam("action"), "create")) {
      model.put("popup_title", "Error en la creacion");
      model.put("popup_message", "Ocurrio un error al crear al tecnico");
      model.put("popup_ruta", "/tecnico");
      ctx.render("admin/registro_tecnicos.hbs", model);
    } else
      ctx.render("admin/registro_tecnicos.hbs", model);

  }

  // POST /tecnico
  public void crearTecnico(Context ctx) {
    HashMap<String, Object> model = new HashMap<>();
    setModel(model,ctx);
    try {
      String infoContacto = ctx.formParam("infoContacto");
      MedioContacto medioContacto = MedioDeContactoFactory.crearMedioDeContactoDeString(ctx.formParam("medioContacto"));
      if (medioContacto instanceof WhatsApp) {
        if (infoContacto != null && !infoContacto.startsWith("549")) {
          infoContacto = "549" + infoContacto;
        }
      }
      Contacto contacto = new Contacto(infoContacto,medioContacto);

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
            Provincia.valueOf(ctx.formParam("provincia")),
            Integer.parseInt(ctx.formParam("codigo_postal"))),
            Double.parseDouble(ctx.formParam("radioCobertura")
          )
        ))
        .contactos(List.of(contacto))
        .build();

      tecnico.setUsuario(usuariosController.crearUserTecnico(tecnico));
      repositorio.guardar(tecnico);

      ctx.redirect("/registro/tecnico?exito=true&action=create");
      Logger.debug("Tecnico creado correctamente");
    } catch (Exception e) {
      ctx.redirect("/registro/tecnico?exito=false&action=create");
      //ctx.status(500);
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
      Logger.debug("Tecnico eliminado correctamente");
     // ctx.redirect("/tecnico?exito=true&action=delete");
      ctx.status(200);
    } catch (Exception e) {
      Logger.debug("Error al eliminar el tecnico: " + e.getMessage());
     // ctx.redirect("/tecnico?exito=false&action=delete");
      ctx.status(500);
    }
  }

  private void setModel(HashMap<String, Object> model, Context ctx) {
    model.put("tecnico", "seleccionado");
    model.put("titulo", "Técnicos");
    model.put("username", ctx.sessionAttribute("user_name"));

    if (ctx.sessionAttribute("user_type") == "ADMIN") {
      model.put("esAdmin", "true");
      model.put("user_type", "admin");
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

