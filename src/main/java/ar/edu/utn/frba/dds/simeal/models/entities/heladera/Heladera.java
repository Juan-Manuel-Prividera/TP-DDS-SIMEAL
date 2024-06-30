package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.Activa;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.EnReparacion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.EstadoHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.Inactiva;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.AdministradorDeEventos;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.Evento;
import ar.edu.utn.frba.dds.simeal.models.entities.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.HuboUnDesperfecto;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.IncidenteRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.TecnicoRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.VisitaTecnicaRepository;
import ar.edu.utn.frba.dds.simeal.service.Notificador;
import ar.edu.utn.frba.dds.simeal.service.logger.Logger;
import ar.edu.utn.frba.dds.simeal.service.logger.LoggerType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Getter
@NoArgsConstructor
public class Heladera {
  @Setter
  private String nombre;
  private Ubicacion ubicacion;
  private LocalDate fechaColocacion;
  private EstadoHeladera estado;
  private Modelo modelo;

  private AdministradorDeEventos administradorDeEventos;

  public Heladera(Ubicacion ubicacion, LocalDate fechaColocacion, String nombre, Modelo modelo,
                  AdministradorDeEventos administradorDeEventos) {
    this.ubicacion = ubicacion;
    this.fechaColocacion = fechaColocacion;
    this.nombre = nombre;
    this.modelo = modelo;
    this.administradorDeEventos = administradorDeEventos;
  }
  public Heladera(Ubicacion ubicacion, AdministradorDeEventos administradorDeEventos) {
    this.ubicacion = ubicacion;
    this.administradorDeEventos = administradorDeEventos;
  }


  public void cambiarDeEstado(EstadoHeladera nuevoEstado) {
    this.estado = nuevoEstado;
  }

  public boolean estaDisponible() {
    return this.estado.disponible();
  }

  public boolean temperaturaAdecuada(double temp) {
    return temp >= modelo.getTemperaturaMin() && temp <= modelo.getTemperaturaMax();
  }

  public void reportarIncidente(Incidente incidente) {
    // Avisa al admnistrador de eventos que hubo un incidente en esta heladera
    administradorDeEventos
        .huboUnEvento(new Evento(this, TipoEvento.INCIDENTE, new HuboUnDesperfecto(this)));

    this.estado = new Inactiva();
    Mensaje mensaje = generarMensaje(incidente);

    // La consigna dice 'reportar', yo lo loggeo pero se podría hacer lo que quisiesemos con esta data.
    // Se logea lo mismo que se le envía al técnico pero se podría mandar lo que quisieramos.
    Logger.getInstance().log(LoggerType.INFORMATION, mensaje.getMensaje());
    IncidenteRepository.getInstance().guardar(incidente);

    Notificador.notificar(TecnicoRepository.getInstance().buscarMasCercanoA(ubicacion), mensaje);

  }

  public void registrarVisita(VisitaTecnica visita) {
    VisitaTecnicaRepository.getInstance().guardar(visita);

    if (visita.getExitosa()) this.estado = new Activa();
    else this.estado = new EnReparacion();
  }

  private Mensaje generarMensaje(Incidente incidente) {
    DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    String msj = "Un incidente en la heladera \"" + this.nombre + "\" ha sido reportado:\n"
        + incidente.getNotificacion();
    String asunto = "Incidente reportado en " + this.ubicacion.getStringUbi() + ".";

    return new Mensaje(msj, asunto);
  }
}
