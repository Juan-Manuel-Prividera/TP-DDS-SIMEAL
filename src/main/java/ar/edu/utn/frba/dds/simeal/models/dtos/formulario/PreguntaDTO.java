package ar.edu.utn.frba.dds.simeal.models.dtos.formulario;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Opcion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario.Pregunta;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreguntaDTO {
    private String campo;
    private String type;
    private String param;
    private String required;
    private List<OpcionDTO> opciones;
    private Boolean esChoice;
    private String id;

    public PreguntaDTO(Pregunta pregunta) {
        this.campo = pregunta.getPregunta();
        this.param = pregunta.getParam();
        this.type = String.valueOf(pregunta.getTipo());
        if (String.valueOf(pregunta.getTipo()).equals("CHOICE")) {
            this.esChoice = true;
        }
        this.required = String.valueOf(pregunta.getRequired());
        this.opciones = new ArrayList<>();
        for (Opcion opcion : pregunta.getOpciones()) {
            opciones.add(new OpcionDTO(opcion.getNombre()));
        }
        this.id = String.valueOf(pregunta.getId());
    }
}
