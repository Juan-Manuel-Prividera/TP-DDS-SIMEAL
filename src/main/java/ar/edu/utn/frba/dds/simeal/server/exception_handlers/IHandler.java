package ar.edu.utn.frba.dds.simeal.server.exception_handlers;

import io.javalin.Javalin;

public interface IHandler {
    void setHandler(Javalin app);
}
