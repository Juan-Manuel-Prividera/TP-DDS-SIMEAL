package ar.edu.utn.frba.dds.simeal.controllers;


import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.repositories.OfertaRepository;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfertasController {
  private OfertaRepository ofertaRepository = (OfertaRepository) ServiceLocator.getRepository(OfertaRepository.class);

  public void index(Context app){
    Map<String, Object> model = new HashMap<>();
    List<Oferta> ofertas = (List<Oferta>) ofertaRepository.obtenerTodos(Oferta.class);

    model.put("ofertas", ofertas);
    model.put("titulo", "Simeal - Lista de productos");

    //TODO: Cambiar forma en la que diferencia juridico de humano
    if(app.sessionAttribute("user_type").equals("juridico")){
      app.render("/ofertas/ofertas_juridico.hbs", model);
    } else if(app.sessionAttribute("user_type").equals("personal")){
      app.render("/ofertas/ofertas_personal.hbs", model);
    }
  }

  public void show(Context app){
    Map<String, Object> model = new HashMap<>();
    //TODO: Buscar la forma de no tener que volver a cargar todas las oferta de la BD
    List<Oferta> ofertas = (List<Oferta>) ofertaRepository.obtenerTodos(Oferta.class);
    Oferta oferta = (Oferta) ofertaRepository.buscarPorId(Long.valueOf(app.pathParam("oferta_id")), Oferta.class);

    model.put("titulo", "Simeal - Lista de productos");
    model.put("ofertas", ofertas);
    model.put("oferta", oferta);
    model.put("href_oferta", "oferta_juridico.hbs");

    if(app.sessionAttribute("user_type").equals("juridico")){
      app.render("/ofertas/oferta_juridico.hbs", model);
    } else if(app.sessionAttribute("user_type").equals("personal")){
      app.render("/ofertas/oferta_personal.hbs", model);
    }
  }
}

