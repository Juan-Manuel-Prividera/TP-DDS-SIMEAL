package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

import static org.hibernate.annotations.CascadeType.MERGE;
import static org.hibernate.annotations.CascadeType.PERSIST;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="formularios")
public class Formulario extends Persistente {
    @Cascade({MERGE,PERSIST})
    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
            name = "formulario_pregunta",
            joinColumns = @JoinColumn( name="formulario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pregunta_id", referencedColumnName = "id"))
    private List<Pregunta> preguntas;
    private Boolean enUso;
    private String nombre;
}
