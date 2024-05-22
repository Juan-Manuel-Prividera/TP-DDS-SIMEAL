package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.medioContacto.MedioContacto;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.AreaDeCobertura;

import java.util.List;

public class Tecnico {
  String nombre;
  String apellido;
  Documento documento;
  String cuil;
  List<MedioContacto> mediosDeContacto;
  AreaDeCobertura areaDeCobertura;

  public Tecnico(String apellido, String nombre, Documento documento, String cuil, List<MedioContacto> mediosDeContacto, AreaDeCobertura areaDeCobertura) {
    this.apellido = apellido;
    this.nombre = nombre;
    this.documento = documento;
    this.cuil = cuil;
    this.mediosDeContacto = mediosDeContacto;
    this.areaDeCobertura = areaDeCobertura;
  }
}

