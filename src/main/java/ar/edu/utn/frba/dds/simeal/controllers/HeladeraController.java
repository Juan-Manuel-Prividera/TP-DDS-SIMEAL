package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.models.dtos.HeladeraDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HeladeraController {
  private Repositorio heladeraRepositorio;

  public HeladeraController(Repositorio heladeraRepository) {
    this.heladeraRepositorio = heladeraRepository;
  }

  public void index(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model);

    app.render("/heladeras/home_heladera.hbs",model);
  }

  public void getAll(Context app) {
    List<Heladera> heladeras = (List<Heladera>) heladeraRepositorio.obtenerTodos(Heladera.class);
    List<HeladeraDTO> heladerasDTOS = new ArrayList<>();

    for (Heladera heladera : heladeras) {
      heladerasDTOS.add(new HeladeraDTO(heladera));
    }

    app.json(heladerasDTOS);
  }
  
  private void setNavBar(HashMap<String,Object> model) {
    model.put("titulo", "Home Heladera");
    // TODO: Hacer un if con el rol de la sesion
    model.put("esHumano", "true");
    model.put("heladeras", "seleccionado");
    model.put("username", "sacar de la sesion :)");
  }

  public void getEstadoHeladera(Context context) {

  }
}
