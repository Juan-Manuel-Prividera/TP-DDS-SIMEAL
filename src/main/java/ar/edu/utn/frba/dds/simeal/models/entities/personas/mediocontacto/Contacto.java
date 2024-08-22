package ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto;

import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class Contacto {
    private String infoDeContacto;
    private MedioContacto medioContacto;

    public void notificar(Mensaje mensaje) {
        medioContacto.notificar(infoDeContacto,mensaje);
    }
}
