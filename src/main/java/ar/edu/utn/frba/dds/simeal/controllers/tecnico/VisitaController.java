package ar.edu.utn.frba.dds.simeal.controllers.tecnico;

import ar.edu.utn.frba.dds.simeal.models.dtos.EncargoTecnicoDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.VisitaTecnicaDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.EncargoTecnico;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.VisitaTecnica;
import ar.edu.utn.frba.dds.simeal.models.repositories.EncargoTecnicoRepostiry;
import ar.edu.utn.frba.dds.simeal.models.repositories.VisitaTecnicaRepository;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VisitaController {
  private VisitaTecnicaRepository visitaTecnicaRepository;
  private EncargoTecnicoRepostiry encargoTecnicoRepostiry;

  public VisitaController(VisitaTecnicaRepository visitaTecnicaRepository, EncargoTecnicoRepostiry encargoTecnicoRepostiry)  {
    this.visitaTecnicaRepository = visitaTecnicaRepository;
    this.encargoTecnicoRepostiry = encargoTecnicoRepostiry;
  }

  // Este index es como el home del tecnico asi que tambien tiene los encargos
  // Podria verse de acomodar de otra forma las cosas pero de momento queda
  public void index(Context ctx) {
    List<VisitaTecnica> visitas = visitaTecnicaRepository.getPorTecnico(ctx.sessionAttribute("tecnico_id"));
    List<VisitaTecnicaDTO> visitasDTO = new ArrayList<>();
    for (VisitaTecnica visita : visitas) {
      visitasDTO.add(new VisitaTecnicaDTO(visita));
    }
    List<EncargoTecnico> encargos = encargoTecnicoRepostiry.getPorTecnico(ctx.sessionAttribute("tecnico_id"));
    List<EncargoTecnicoDTO> encargosDTO = new ArrayList<>();
    for (EncargoTecnico encargo : encargos) {
      encargosDTO.add(new EncargoTecnicoDTO(encargo));
    }
    HashMap<String, Object> model = new HashMap<>();
    model.put("visitas", visitasDTO);
    model.put("encargos", encargosDTO);
    ctx.render("tecnico_home.hbs",model);
  }

  // GET /{encargo_id}/visita
  public void indexRegistroVisita(Context ctx) {
    // TODO: Ver que mas hace falta aca
    ctx.render("registro_visita.hbs");
  }

  // POST /{encargo_id}/visita
  public void registrar(Context ctx) {
    EncargoTecnico encargo = (EncargoTecnico) encargoTecnicoRepostiry
      .buscarPorId(Long.valueOf(ctx.pathParam("encargo_id")),EncargoTecnico.class);

    VisitaTecnica visitaTecnica = VisitaTecnica.builder()
      .descripcion(ctx.formParam("descripcion"))
      .exitosa(Boolean.valueOf(ctx.formParam("exitosa")))
      .fechaHora(LocalDateTime.parse(ctx.formParam("fechaHora")))
      .imagen(ctx.formParam("imagen"))
      .heladera(encargo.getIncidente().getHeladera())
      .build();

    if (visitaTecnica.getExitosa()) {
      // Si la visita fue exitosa cumplio el encargo entonces lo desactivo
      encargoTecnicoRepostiry.desactivar(encargo);
      // Y la heladera ya estaria reparada asi que la activamos
      encargo.getIncidente().getHeladera().activar();
    }
    encargo.incrementVisitasHechas();
    encargoTecnicoRepostiry.actualizar(encargo);
    encargoTecnicoRepostiry.refresh(encargo);
  }
}
