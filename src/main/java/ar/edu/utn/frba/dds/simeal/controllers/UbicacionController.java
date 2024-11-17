package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Localidad;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Provincia;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class UbicacionController {
  private Repositorio repositorio;

  public UbicacionController(Repositorio repositorio) {
    this.repositorio = repositorio;
  }

  public void getLocalidades(Context ctx) {
    List<Localidad> localidades  = (List<Localidad>) repositorio.obtenerTodos(Localidad.class);
    Provincia provincia = Provincia.valueOf(ctx.queryParam("provincia"));
    List<Localidad> localidadesProvincia = new ArrayList<>();
    for (Localidad localidad : localidades) {
      if (localidad.getProvincia().equals(provincia)) {
        localidadesProvincia.add(localidad);
      }
    }
    ctx.json(localidadesProvincia);
  }
}
