package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Rubro;
import ar.edu.utn.frba.dds.simeal.models.entities.formulario.FormularioContestado;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Tarjeta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TarjetaColaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.MedioContacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.service.CalculadorDeReconocimientos;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;



@Getter
public class Colaborador {
  private String nombre;
  private String apellido;
  private final Documento documento;
  private Ubicacion direccion;
  private String razonSocial;
  private Rubro rubro;
  private TipoJuridico tipoJuridico;
  private FormularioContestado formularioContestado;
  private final List<MedioContacto> mediosDeContacto = new ArrayList<>();
  private final List<TipoColaboracion> formasDeColaborar = new ArrayList<>();

  @Setter
  private double puntosDeReconocimientoParcial;

  public Colaborador(Documento documento, String nombre, String apellido) {
    this.documento = documento;
    this.nombre = nombre;
    this.apellido = apellido;
  }

  public void addMedioContacto(MedioContacto medioContacto) {
    mediosDeContacto.add(medioContacto);
  }

  public void gastarPuntos(double puntos) {
    this.puntosDeReconocimientoParcial -= puntos;
  }

  public void sumarPuntosReconocimiento(double puntosDeReconocimiento) {
    this.puntosDeReconocimientoParcial += puntosDeReconocimiento;
  }

  // Le tienen que llegar los puntos que le aporta las heladeras, cuando tengamos persistencia
  public boolean puedeCanjear(Oferta oferta, double puntosPorHeladeras) {
    return this.puntosDeReconocimientoParcial + puntosPorHeladeras >= oferta.getPuntosNecesarios();
  }
}

