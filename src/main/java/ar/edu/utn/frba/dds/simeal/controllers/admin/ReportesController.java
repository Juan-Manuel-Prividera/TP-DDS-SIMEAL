package ar.edu.utn.frba.dds.simeal.controllers.admin;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.dtos.ReporteDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.VisitaTecnicaDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.VisitaTecnica;
import ar.edu.utn.frba.dds.simeal.models.repositories.VisitaTecnicaRepository;
import ar.edu.utn.frba.dds.simeal.service.cronjobs.GenerarReporte;
import io.javalin.http.Context;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportesController {
  public void reportes(Context app) throws IOException {
    Map<String, Object> model = new HashMap<>();
    model.put("reporte", "seleccionado");
    model.put("user_type", "admin");
    model.put("esAdmin", true);
    model.put("username", app.sessionAttribute("user_name"));
    model.put("titulo", "Reportes");

    String directoryPath = "src/main/resources/dinamic/reportes";

    File directory = new File(directoryPath);

    if (!directory.exists())
        directory.mkdirs();
    List<Path> filePaths = Files.walk(Path.of("src/main/resources/dinamic/reportes"))
      .filter(Files::isRegularFile) // Filtrar solo archivos
      .toList(); // Guardar en una lista
    List<ReporteDTO> reportes = new ArrayList<>();

    for (Path path : filePaths) {
        String fileName = path.getFileName().toString();
      reportes.add(new ReporteDTO("reportes/"+fileName, fileName));
    }
    model.put("reportes", reportes);

    VisitaTecnicaRepository visitaTecnicaRepository = (VisitaTecnicaRepository) ServiceLocator
      .getRepository(VisitaTecnicaRepository.class);
   List<VisitaTecnica> visitas = (List<VisitaTecnica>) visitaTecnicaRepository.obtenerTodos(VisitaTecnica.class);
    List<VisitaTecnicaDTO> visitaTecnicaDTOS = new ArrayList<>();
    for (VisitaTecnica v : visitas) {
        visitaTecnicaDTOS.add(new VisitaTecnicaDTO(v));
    }
    model.put("visitas", visitaTecnicaDTOS);
    app.render("admin/reportes.hbs", model);
  }

  public void generarReporte(Context ctx) {
    GenerarReporte.main(null);
    ctx.redirect("/reportes");
  }
}
