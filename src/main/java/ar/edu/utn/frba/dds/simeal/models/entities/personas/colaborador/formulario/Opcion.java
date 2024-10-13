package ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.formulario;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="opciones")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Opcion extends Persistente {
    @Column(name="nombre")
    private String nombre;
}
