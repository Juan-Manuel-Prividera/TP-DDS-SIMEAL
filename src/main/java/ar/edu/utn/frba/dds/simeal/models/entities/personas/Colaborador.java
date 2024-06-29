package ar.edu.utn.frba.dds.simeal.models.entities.personas;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.TipoColaboracion;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Rubro;
import ar.edu.utn.frba.dds.simeal.models.entities.formulario.FormularioContestado;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TarjetaColaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.mediocontacto.MedioContacto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.entities.usuario.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;



@Getter
@NoArgsConstructor
public class Colaborador implements ReceptorDeNotificaciones {
  private String nombre;
  private String apellido;
  private Documento documento;
  private Ubicacion ubicacion;
  private String razonSocial;
  private Rubro rubro;
  private TipoJuridico tipoJuridico;
  private FormularioContestado formularioContestado;
  private final List<MedioContacto> mediosDeContacto = new ArrayList<>();
  private MedioContacto medioDeContactoPreferido;
  private final List<TipoColaboracion> formasDeColaborar = new ArrayList<>();
  private int cantidadDeViandasAceptable;
  @Setter
  private Usuario usuario;
  @Setter
  private double puntosDeReconocimientoParcial;

  public Colaborador(Documento documento, String nombre, String apellido) {
    this.documento = documento;
    this.nombre = nombre;
    this.apellido = apellido;
  }

  public Colaborador(int cantidadDeViandasAceptable, Ubicacion ubicacion) {
    this.cantidadDeViandasAceptable = cantidadDeViandasAceptable;
    this.ubicacion = ubicacion;
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

  @Override
  public void recibirNotificacion(Mensaje mensaje) {
    medioDeContactoPreferido.notificar(mensaje);
  }


}

