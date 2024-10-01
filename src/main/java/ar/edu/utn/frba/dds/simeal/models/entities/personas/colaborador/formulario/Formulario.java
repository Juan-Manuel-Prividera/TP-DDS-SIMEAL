package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="formularios")
public class Formulario extends Persistente {
    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
            name = "formulario_pregunta",
            joinColumns = @JoinColumn( name="formulario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pregunta_id", referencedColumnName = "id"))
    private List<Pregunta> preguntas;

}
