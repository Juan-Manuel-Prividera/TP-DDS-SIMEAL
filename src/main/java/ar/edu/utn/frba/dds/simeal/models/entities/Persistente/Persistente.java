package ar.edu.utn.frba.dds.simeal.models.entities.Persistente;

import lombok.Getter;
import lombok.Setter;

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

    @Setter
    @Column(name="activo", nullable = false)
    private Boolean activo;

    public Persistente() {
        fechaAlta = LocalDate.now();
    }

}
