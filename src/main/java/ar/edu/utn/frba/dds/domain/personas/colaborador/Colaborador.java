package ar.edu.utn.frba.dds.domain.personas.colaborador;

import ar.edu.utn.frba.dds.domain.Rubro;
import ar.edu.utn.frba.dds.domain.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.domain.formulario.FormularioContestado;
import ar.edu.utn.frba.dds.domain.heladera.Heladera;
import ar.edu.utn.frba.dds.domain.personas.medioContacto.MedioContacto;
import ar.edu.utn.frba.dds.domain.ubicacion.Ubicacion;
import java.util.List;

public class Colaborador {
  String nombre;
  String apellido;
  String razonSocial;
  Rubro rubro;
  Ubicacion direccion;
  List<Heladera> heladeras;
  List<MedioContacto> mediosDeContacto;
//  List<Colaboracion> colaboraciones;
  TipoColaborador tipoColaborador;
  FormularioContestado formularioContestado;
  float puntosDeReconocimiento;
//  List<Tarjeta> tarjetas;

  public void colaborar() {}
  public void calcularReconocimiento() {}


}
