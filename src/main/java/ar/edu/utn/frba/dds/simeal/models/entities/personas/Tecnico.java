package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.MedioContacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.AreaDeCobertura;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Tecnico {
  String nombre;
  String apellido;
  Documento documento;
  String cuil;
  List<MedioContacto> mediosDeContacto;
  AreaDeCobertura areaDeCobertura;


}

