package ar.edu.utn.frba.dds.domain.personas.tecnico;

import ar.edu.utn.frba.dds.domain.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.domain.personas.medioContacto.MedioContacto;
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

