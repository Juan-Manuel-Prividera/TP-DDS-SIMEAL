package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.converters.MedioContactoConverter;
import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
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


    @Convert(converter = MedioContactoConverter.class)
    @Column(name = "medio_contacto")
    private MedioContacto medioContacto;

    public void notificar(Mensaje mensaje) {
        medioContacto.notificar(infoDeContacto,mensaje);
    }
}
