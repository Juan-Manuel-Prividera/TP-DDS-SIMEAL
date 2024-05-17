package ar.edu.utn.frba.dds.domain.heladera;

import ar.edu.utn.frba.dds.domain.heladera.estados.EstadoHeladera;
import ar.edu.utn.frba.dds.domain.heladera.sensor.Sensor;
import ar.edu.utn.frba.dds.domain.ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
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
  private List<Vianda> viandas; //Habria que ver si esto sigue
  private EstadoHeladera estado;
  private Modelo modelo;

  private AdministradorAlertas administradorAlertas;
  private List<Sensor> sensores;
  private List<Desperfecto> desperfectos;
  private Float ultimaTempRegistrada;

  public Heladera(Ubicacion ubicacion, LocalDate fechaColocacion, String nombre, Modelo modelo) {
    this.ubicacion = ubicacion;
    this.fechaColocacion = fechaColocacion;
  }

  //TODO

  //TESTEAR ESTOS DOS METODOS
  public void retirarViandas(List<Vianda> viandas) {
    this.viandas.removeAll(viandas);
  }

  public void ingresarViandas(List<Vianda> viandas) {
    this.viandas.addAll(viandas);
  }
}
