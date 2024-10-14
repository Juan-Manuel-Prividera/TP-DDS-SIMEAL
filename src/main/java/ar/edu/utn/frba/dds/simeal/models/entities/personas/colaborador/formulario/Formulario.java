package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.usuario.TipoRol;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.annotations.CascadeType.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="formularios")
@NamedEntityGraph(
  name = "Formulario.preguntas",
  attributeNodes = @NamedAttributeNode("preguntas")
)
public class Formulario extends Persistente {
    @Cascade({MERGE,PERSIST})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "formulario_pregunta",
            joinColumns = @JoinColumn( name="formulario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pregunta_id", referencedColumnName = "id"))
    private List<Pregunta> preguntas;

    @Column(name = "en_uso")
    private Boolean enUso;

    @Column(name = "tipo_rol")
    @Enumerated(EnumType.STRING)
    private TipoRol rol;

    @Column(name = "nombre")
    private String nombre;

    public void addPregunta(Pregunta pregunta) {
        if (preguntas == null) {
            preguntas = new ArrayList<>();
        }
        preguntas.add(pregunta);
    }
    public List<Pregunta> getPreguntas() {
        if (preguntas == null) {
            preguntas = new ArrayList<>();
        }
        return preguntas;
    }
}
