package ar.edu.utn.frba.dds.simeal.models.dtos.formulario;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Formulario;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Pregunta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormularioDTO {
  private List<PreguntaDTO> preguntas;
  private String enUso;
  private String nombre;
  private String id;
  private String fechaCreacion;

  public FormularioDTO(Formulario formulario) {
    preguntas = new ArrayList<>();
    for (Pregunta pregunta : formulario.getPreguntas()) {
      preguntas.add(new PreguntaDTO(pregunta));
    }
    this.enUso = String.valueOf(formulario.getEnUso());
    this.nombre = formulario.getNombre();
    this.id = String.valueOf(formulario.getId());
    this.fechaCreacion = formulario.getFechaAlta().toString();
  }
}
