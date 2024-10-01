package ar.edu.utn.frba.dds.simeal.models.dtos;

import lombok.*;

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
}
