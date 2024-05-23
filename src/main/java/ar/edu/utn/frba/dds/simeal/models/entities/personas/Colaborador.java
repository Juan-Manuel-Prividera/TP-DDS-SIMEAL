package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Rubro;
import ar.edu.utn.frba.dds.simeal.models.entities.formulario.FormularioContestado;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.medioContacto.MedioContacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter
public class Colaborador {
  String nombre;
  String apellido;
  Documento documento;
  Ubicacion direccion;
  String razonSocial;
  Rubro rubro;
  List<Heladera> heladeras;
  List<MedioContacto> mediosDeContacto = new ArrayList<>();
  TipoJuridico tipoJuridico;
  FormularioContestado formularioContestado;
  double puntosDeReconocimiento;

  public Colaborador(Documento documento, String nombre, String apellido) {
    this.documento = documento;
    this.nombre = nombre;
    this.apellido = apellido;
  }

  public void sumarPuntosReconocimientos(double puntos){
    puntosDeReconocimiento += puntos;
  }
  public void addMedioContacto(MedioContacto medioContacto) {
    mediosDeContacto.add(medioContacto);
  }

  public boolean puedeCanjear(Oferta oferta){
    return puntosDeReconocimiento >= oferta.getPuntosNecesarios();
  }
}
