package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import ar.edu.utn.frba.dds.simeal.models.entities.Mensaje;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.Activa;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.EnReparacion;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.EstadoHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.Inactiva;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ar.edu.utn.frba.dds.simeal.service.logger.Logger;
import ar.edu.utn.frba.dds.simeal.service.logger.LoggerType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
public class Heladera {
  @Setter
  private String nombre;
  private Ubicacion ubicacion;
  private LocalDate fechaColocacion;
  private EstadoHeladera estado;
  private List<Incidente> incidentes;
  private Modelo modelo;

  public Heladera(Ubicacion ubicacion, LocalDate fechaColocacion, String nombre, List<Incidente> incidentes, Modelo modelo) {
    this.ubicacion = ubicacion;
    this.fechaColocacion = fechaColocacion;
    this.nombre = nombre;
    this.incidentes = incidentes;
    this.modelo = modelo;
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
    this.estado = new Inactiva();

    DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    String msj = "Un incidente en la heladera \"" + this.nombre + "\" ha sido reportado:\n"
        + incidente.getNotificacion();
    String asunto = "Incidente reportado en " + this.ubicacion.getNombreCalle() + " " + this.ubicacion.getAltura() + ".";

    Mensaje mensaje = new Mensaje(msj, asunto);

    Logger logger = Logger.getInstance();

    // La consigna dice 'reportar', yo lo loggeo pero se podría hacer lo que quisiesemos con esta data.
    // Se logea lo mismo que se le envía al técnico pero se podría mandar lo que quisieramos.
    logger.log(LoggerType.INFORMATION, msj);

    // Quizás guardar el histórico? Levantar BD y guardar el incidente?

    this.incidentes.add(incidente);


    // Levantar la BD y buscar la suscripción de tecnicos asociada a esta heladera.
    // Suscripcion suscripcion;
    // suscripcion.notificarAlPrimero(mensaje);
  }

  public void registrarVisita(VisitaTecnica visita) {
    // TODO Persistir visita

    if (visita.getExitosa()) this.estado = new Activa();
    else this.estado = new EnReparacion();

  }



}
