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
  private final String nombre;
  private final Ubicacion ubicacion;
  private final LocalDate fechaColocacion;
  private EstadoHeladera estado;
  private final Modelo modelo;

  public Heladera(Ubicacion ubicacion, LocalDate fechaColocacion, String nombre, Modelo modelo) {
    this.ubicacion = ubicacion;
    this.fechaColocacion = fechaColocacion;
    this.nombre = nombre;
    this.modelo = modelo;
  }

  public void cambiarDeEstado(EstadoHeladera nuevoEstado) {
    this.estado = nuevoEstado;
  }

  public boolean estaDisponible() {
    return this.estado.disponible();
  }





}
