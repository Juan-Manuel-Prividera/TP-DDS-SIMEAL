package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import ar.edu.utn.frba.dds.simeal.models.creacionales.EventoFactory;
import ar.edu.utn.frba.dds.simeal.models.entities.Persistente.Persistente;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.eventos.TipoEvento;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.notificaciones.Mensaje;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="heladera")
@AllArgsConstructor
public class Heladera extends Persistente {
  @Setter
  @Column(name="nombre")
  private String nombre;

  @Setter
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name="ubicacion_id", referencedColumnName = "id")
  private Ubicacion ubicacion;

  @Column(name="fecha_colocacion")
  private LocalDate fechaColocacion;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name="colaborador_id", referencedColumnName = "id")
  private Colaborador colaboradorACargo;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name="modelo_heladera_id", referencedColumnName = "id")
  private ModeloHeladera modelo;

  @Column(name = "activa")
  private Boolean activa;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(
    name = "heladeras_cercanas",
    joinColumns = @JoinColumn(name = "heladera_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "heladera_cercana_id", referencedColumnName = "id")
  )
  private List<Heladera> heladerasCercanas;



  public Heladera(Ubicacion ubicacion, LocalDate fechaColocacion, String nombre, ModeloHeladera modelo) {
    this.ubicacion = ubicacion;
    this.fechaColocacion = fechaColocacion;
    this.nombre = nombre;
    this.modelo = modelo;
  }

  public Heladera(Ubicacion ubicacion) {
    this.ubicacion = ubicacion;
  }


  public void activar() {
    this.activa = true;
  }
  public void desactivar() {
    this.activa = false;
  }

  public boolean estaDisponible() {
    return activa;
  }

  public boolean temperaturaAdecuada(double temp) {
    return temp >= modelo.getTemperaturaMin() && temp <= modelo.getTemperaturaMax();
  }

  public void reportarIncidente(Incidente incidente) {
    // Esto es solamente para las suscripciones de colaboradores humanos
    EventoFactory.crearEvento(this, TipoEvento.INCIDENTE);

    this.desactivar();

    Mensaje mensaje = generarMensaje(incidente);

    // La consigna dice 'reportar', yo lo loggeo pero se podría hacer lo que quisiesemos con esta data.
    // Se logea lo mismo que se le envía al técnico pero se podría mandar lo que quisieramos.
    Logger.info(mensaje.getMensaje());
  }

  private Mensaje generarMensaje(Incidente incidente) {
    DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    String msj = "Un incidente en la heladera \"" + this.nombre + "\" ha sido reportado:\n"
        + incidente.getNotificacion();
    String asunto = "Incidente reportado en " + this.ubicacion.getStringUbi() + ".";

    return new Mensaje(msj, asunto);
  }

  public void addHeladeraCercana(Heladera heladera) {
    if (this.heladerasCercanas == null) {
      heladerasCercanas = new ArrayList<>();
    }
    heladerasCercanas.add(heladera);
  }
}
