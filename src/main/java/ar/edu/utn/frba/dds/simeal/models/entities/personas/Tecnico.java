package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.Contacto;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.MedioContacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.AreaDeCobertura;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Tecnico implements ReceptorDeNotificaciones {
  private String nombre;
  private String apellido;
  private Documento documento;
  private String cuil;
  private List<Contacto> contactos;
  private Contacto contactoPreferido;
  private AreaDeCobertura areaDeCobertura;


  public Tecnico(Contacto contacto, AreaDeCobertura areaDeCobertura) {
    this.contactoPreferido = contacto;
    this.areaDeCobertura = areaDeCobertura;
  }
  public void recibirNotificacion(Mensaje mensaje) {
    contactoPreferido.notificar(mensaje);
  }



}

