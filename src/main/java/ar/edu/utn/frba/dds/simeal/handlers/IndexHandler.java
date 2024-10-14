package ar.edu.utn.frba.dds.simeal.handlers;

import ar.edu.utn.frba.dds.simeal.models.usuario.TipoRol;
import io.javalin.http.Context;

import java.util.Map;

// Esto es lo primero que ve el usuario cuando llega a SiMeAl
public class IndexHandler {
    // TODO: Implementar recuérdame cookie. Recuéeeerdame, hoy me tengo que ir mi amor, recuérdame. No llores por favor, te llevo en mi corazón y cerca me tendrás. A solas yo te cantaré soñando en regresar. Recuérdame, aunque tengo que migrar, recuérdame si mi guitarra oyes llorar. Ella con su triste canto te acompañará hasta que en mis brazos tu estés, recuérdame.
    public void handle(Context context){

        Boolean fail = Boolean.valueOf(context.queryParam("failed"));
        String tipoUsuarioString = context.sessionAttribute("user_type");

        if (tipoUsuarioString == null) {
            context.render("login.hbs", Map.of("loginFailed", fail));
        } else {
            TipoRol tipoRol = TipoRol.valueOf(tipoUsuarioString);
            //String lastVisited = context.sessionAttribute("last_visited");

            //if (lastVisited != null) context.redirect(lastVisited);

            switch (tipoRol) {
                case ADMIN:
                    context.redirect("/formularios");
                    break;
                case JURIDICO:
                    context.redirect("/home");
                    break;
                case HUMANO:
                    context.redirect("/home");
                    break;
            }
        }

    }
}
