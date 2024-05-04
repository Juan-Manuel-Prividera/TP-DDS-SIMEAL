package ar.edu.utn.frba.dds.domain.heladera;

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
  private List<Vianda> viandas;
  private estadoHeladera estado;
  private List<Alerta> alertas;
  private List<Desperfecto> desperfectos;
  private Float tempMax;
  private Float tempMin;
  private Float ultimaTempRegistrada;

  //TODO

  //TESTEAR ESTOS DOS METODOS
  public void retirarViandas(List<Vianda> viandas) {
    this.viandas.removeAll(viandas);
  }

  public void ingresarViandas(List<Vianda> viandas) {
    this.viandas.addAll(viandas);
  }
}
