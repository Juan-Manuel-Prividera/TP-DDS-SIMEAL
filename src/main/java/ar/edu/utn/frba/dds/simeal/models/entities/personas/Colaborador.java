package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Rubro;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.formulario.FormularioContestado;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.medioContacto.MedioContacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.service.CalculadorDeReconocimientos;
import ar.edu.utn.frba.dds.simeal.service.ColaboracionBuilder;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
public class Colaborador {
  String nombre;
  String apellido;
  Documento documento;
  Ubicacion direccion;
  String razonSocial;
  Rubro rubro;
  List<Heladera> heladeras;
  List<MedioContacto> mediosDeContacto = new ArrayList<>();
  TipoColaborador tipoColaborador;
  FormularioContestado formularioContestado;
  float puntosDeReconocimiento;

  public Colaborador(Documento documento, String nombre, String apellido) {
  }

  // Chequer... TODO
  public void colaborar(TipoColaboracion tipoColaboracion) {
    ColaboracionBuilder colaboracionFactory = new ColaboracionBuilder();
    colaboracionFactory.crearColaboracion(tipoColaboracion, LocalDate.now(),this,0);
  }

  public void calcularReconocimiento() {
    CalculadorDeReconocimientos calculadorReconocimientos = new CalculadorDeReconocimientos();
    puntosDeReconocimiento = calculadorReconocimientos.calcularReconocimiento(this);
  }


  public void addMedioContacto(MedioContacto medioContacto) {
    mediosDeContacto.add(medioContacto);
  }
}
