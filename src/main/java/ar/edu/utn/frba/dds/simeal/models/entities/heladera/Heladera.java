package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.EstadoHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class Heladera {
  private String nombre;
  private final Ubicacion ubicacion;
  private final LocalDate fechaColocacion;
  private EstadoHeladera estado;
  private Modelo modelo;
  private List<Desperfecto> desperfectos;

  public Heladera(Ubicacion ubicacion, LocalDate fechaColocacion, String nombre, Modelo modelo) {
    this.ubicacion = ubicacion;
    this.fechaColocacion = fechaColocacion;
    this.nombre = nombre;
    this.modelo = modelo;
    desperfectos = new ArrayList<>();
  }

  public void cambiarDeEstado(EstadoHeladera nuevoEstado) {
    this.estado = nuevoEstado;
  }

  public boolean validarEstado() {
    return this.estado.validarEstado();
  }

  public String enviarNotificacionPorDefecto(String mensaje) {
    return this.estado.notificarEstado(mensaje);
  }

  public void agregarDesperfecto(Desperfecto desperfecto) {
    desperfectos.add(desperfecto);
  }

}
