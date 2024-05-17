package ar.edu.utn.frba.dds.domain.personas;

import ar.edu.utn.frba.dds.domain.Rubro;
import ar.edu.utn.frba.dds.domain.formulario.FormularioContestado;
import ar.edu.utn.frba.dds.domain.heladera.Heladera;
import ar.edu.utn.frba.dds.domain.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.domain.personas.medioContacto.Email;
import ar.edu.utn.frba.dds.domain.personas.medioContacto.MedioContacto;
import ar.edu.utn.frba.dds.domain.ubicacion.Ubicacion;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class Colaborador {
  String nombre;
  String apellido;
  String razonSocial;
  Rubro rubro;
  Ubicacion direccion;
  List<Heladera> heladeras;
  List<MedioContacto> mediosDeContacto = new ArrayList<>();
//  List<Colaboracion> colaboraciones;
  TipoColaborador tipoColaborador;
  FormularioContestado formularioContestado;
  Documento documento;

  float puntosDeReconocimiento;

  public Colaborador(Documento documento, String nombre, String apellido) {
  }
//  List<Tarjeta> tarjetas;

  public void colaborar() {}
  public void calcularReconocimiento() {}


  public void addMedioContacto(MedioContacto medioContacto) {
    mediosDeContacto.add(medioContacto);
  }
}
