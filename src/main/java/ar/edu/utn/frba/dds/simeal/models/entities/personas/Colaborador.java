package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Rubro;
import ar.edu.utn.frba.dds.simeal.models.entities.formulario.FormularioContestado;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.medioContacto.MedioContacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.service.CalculadorDeReconocimientos;
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
  List<MedioContacto> mediosDeContacto = new ArrayList<>();
  TipoJuridico tipoJuridico;
  FormularioContestado formularioContestado;
  private CalculadorDeReconocimientos caluladoraDeReconocimientos = new CalculadorDeReconocimientos();
  double puntosDeReconocimientoGastados;
  private List<TipoColaboracion> formasDeColaborar = new ArrayList<>();


  public Colaborador(Documento documento, String nombre, String apellido) {
    this.documento = documento;
    this.nombre = nombre;
    this.apellido = apellido;
  }

  public void addMedioContacto(MedioContacto medioContacto) {
    mediosDeContacto.add(medioContacto);
  }

  public void gastarPuntos(float puntos){
    this.puntosDeReconocimientoGastados += puntos;
  }
  public boolean puedeCanjear(Oferta oferta){
    return caluladoraDeReconocimientos.calcularReconocimientoTotal(this)
        -
        this.puntosDeReconocimientoGastados
        >= oferta.getPuntosNecesarios();
  }
}

