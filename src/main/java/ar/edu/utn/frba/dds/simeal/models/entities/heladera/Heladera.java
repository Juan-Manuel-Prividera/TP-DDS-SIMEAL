package ar.edu.utn.frba.dds.simeal.models.entities.heladera;

import ar.edu.utn.frba.dds.simeal.models.entities.Vianda;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.Activa;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.estados.EstadoHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.AdministradorAlertas;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Heladera {
  private String nombre;
  final private Ubicacion ubicacion;
  final private LocalDate fechaColocacion;
  private EstadoHeladera estado;
  private Modelo modelo;
  private AdministradorAlertas administradorAlertas;
  private List<Desperfecto> desperfectos;
  private Float ultimaTempRegistrada;

  public Heladera(Ubicacion ubicacion, LocalDate fechaColocacion, String nombre, Modelo modelo) {
    this.ubicacion = ubicacion;
    this.fechaColocacion = fechaColocacion;
  }

  public void cambiarDeEstado(EstadoHeladera nuevoEstado){
    this.estado = nuevoEstado;
  }

  public boolean validarEstado(){
    return this.estado.validarEstado();
  }

  public String enviarNotificacionPorDefecto(){
    return this.estado.notificarEstado();
  }

}
