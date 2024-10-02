package ar.edu.utn.frba.dds.simeal.server.handlers;

import io.javalin.Javalin;

public interface IHandler {
    void setHandler(Javalin app);
}
