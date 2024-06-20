package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.MedioContacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.AreaDeCobertura;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Tecnico {
  private String nombre;
  private String apellido;
  private final Documento documento;
  private String cuil;
  private final List<MedioContacto> mediosDeContacto;
  private final AreaDeCobertura areaDeCobertura;


}

