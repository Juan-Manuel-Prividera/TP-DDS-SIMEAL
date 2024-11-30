package ar.edu.utn.frba.dds.simeal.handlers;

import ar.edu.utn.frba.dds.simeal.models.dtos.ReporteDTO;
import io.javalin.http.Context;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeHandler {
    public void handle(Context context) throws IOException {
        String username = context.sessionAttribute("user_name");
        if (username == null) {
            username = "NOT_FOUND \uD83D\uDC80";
        }

        String rol = context.sessionAttribute("user_type");
        if (rol == null) {
            context.redirect("/");
                    return;
        }

        HashMap<String, Object> hbMap = new HashMap<>();

        switch (rol){
//            case "ADMIN":
//                hbMap.put("esAdmin", true);
//                hbMap.put("user_type", "admin");
//                hbMap.put("formulario", "seleccionado");
//                break;
            case "HUMANO":
                hbMap.put("esHumano", true);
                hbMap.put("user_type", "humano");
                break;
            case "JURIDICO":
                hbMap.put("esJuridico", true);
                hbMap.put("user_type", "juridico");

                String directoryPath = "src/main/resources/dinamic/reportes";

                File directory = new File(directoryPath);

                if (!directory.exists())
                    directory.mkdirs();
                List<Path> filePaths = Files.walk(Path.of("src/main/resources/dinamic/reportes"))
                  .filter(Files::isRegularFile) // Filtrar solo archivos
                  .toList(); // Guardar en una lista
                List<ReporteDTO> reportes = new ArrayList<>();

                for (Path path : filePaths) {
                    String filename = path.getFileName().toString();
                    reportes.add(new ReporteDTO("/reportes/"+filename, filename));
                }
                hbMap.put("reportes", reportes);
                break;
        }

        hbMap.put("username", username);
        hbMap.put("titulo", "Simeal - Home");
        context.render("home.hbs", hbMap);
    }
}
