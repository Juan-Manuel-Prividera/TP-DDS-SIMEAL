package ar.edu.utn.frba.dds.simeal.server.handlers;


import io.javalin.Javalin;

import java.util.Arrays;

// Dudo que alguien más quiera usar esto, pero si por alguna razón quieren handlear una excepción, se declara acá
public class AppHandlers {
    private IHandler[] handlers = new IHandler[]{
            new ForbiddenHandler(),
            new NotAuthenticatedHandler(),
    };

    public static void applyHandlers(Javalin app){
        Arrays.stream(new AppHandlers().handlers).toList().forEach(handler -> handler.setHandler(app));
    }

}

