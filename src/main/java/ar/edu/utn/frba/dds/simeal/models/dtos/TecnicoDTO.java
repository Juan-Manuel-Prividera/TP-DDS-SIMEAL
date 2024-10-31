package ar.edu.utn.frba.dds.simeal.models.dtos;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.Tecnico;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TecnicoDTO {
  private String id;
  private String nombre;
  private String apellido;
  private String areaDeCobertura;

  public TecnicoDTO(Tecnico tecnico) {
    this.id = String.valueOf(tecnico.getId());
    this.nombre = tecnico.getNombre();
    this.apellido = tecnico.getApellido();
    this.areaDeCobertura = tecnico.getAreaDeCobertura().getUbicacion().getStringUbi() +
      ", radio:  " + tecnico.getAreaDeCobertura().getRadioDeCobertura() + "km";
  }
}
