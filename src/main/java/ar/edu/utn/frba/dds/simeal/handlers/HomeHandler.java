package ar.edu.utn.frba.dds.simeal.handlers;

import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class HomeHandler {
    public void handle(Context context) {
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
                break;
        }

        hbMap.put("username", username);
        hbMap.put("titulo", "Simeal - Home");
        context.render("home.hbs", hbMap);
    }
}
