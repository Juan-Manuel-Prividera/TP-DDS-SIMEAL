package ar.edu.utn.frba.dds.simeal.models.entities.Persistente;

import lombok.Getter;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@MappedSuperclass
@Getter
public class Persistente {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name="fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    public Persistente() {
        fechaAlta = LocalDate.now();
    }

}
