package ar.edu.utn.frba.dds.simeal.controllers;

import ar.edu.utn.frba.dds.simeal.models.dtos.UbicacionDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Provincia;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class RecomendacionUbicacionController {
  private Repositorio repositorio;

  public RecomendacionUbicacionController(Repositorio repositorio) {
    this.repositorio = repositorio;
  }
  public void index(Context ctx) {
    List<Ubicacion> ubicacionesRecomendadas = List.of(
      (Ubicacion) repositorio.buscarPorId(143L, Ubicacion.class)
    );
    List<UbicacionDTO> ubicaciones = new ArrayList<>();
    for(Ubicacion ubicacion : ubicacionesRecomendadas) {
      ubicaciones.add(new UbicacionDTO(ubicacion));
    }

    ctx.json(ubicaciones);
  }

}
