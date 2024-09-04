package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "contacto")
public class Contacto extends Persistente {
    @Column(name = "infoDeContacto")
    private String infoDeContacto;
    //TODO   (un converter tendría mucha lógica, pensar en que hacer...)
    private MedioContacto medioContacto;

    public void notificar(Mensaje mensaje) {
        medioContacto.notificar(infoDeContacto,mensaje);
    }
}
