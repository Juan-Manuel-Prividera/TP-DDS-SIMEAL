package ar.edu.utn.frba.dds.simeal.handlers;

import io.javalin.http.Context;

// Esto es lo primero que ve el usuario cuando llega a SiMeAl
public class IndexHandler {
    // TODO: Implementar recuérdame cookie. Recuéeeerdame, hoy me tengo que ir mi amor, recuérdame. No llores por favor, te llevo en mi corazón y cerca me tendrás. A solas yo te cantaré soñando en regresar. Recuérdame, aunque tengo que migrar, recuérdame si mi guitarra oyes llorar. Ella con su triste canto te acompañará hasta que en mis brazos tu estés, recuérdame.
    public void handle(Context context){
        context.render("login.hbs");
    }
}
