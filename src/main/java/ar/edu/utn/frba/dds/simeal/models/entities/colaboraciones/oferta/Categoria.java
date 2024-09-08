package ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
    @Column(name = "categoria")
    private String nombre;
}
