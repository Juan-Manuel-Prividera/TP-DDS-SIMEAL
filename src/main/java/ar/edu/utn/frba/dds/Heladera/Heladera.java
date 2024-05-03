package ar.edu.utn.frba.dds.Heladera;

import ar.edu.utn.frba.dds.Ubicacion.Ubicacion;
import ar.edu.utn.frba.dds.Viandas.Vianda;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class Heladera {
  @Getter private String nombre;
  @Getter final private Ubicacion ubicacion;
  @Getter final private LocalDate fechaColocacion;
  @Getter private List<Vianda> viandas;
  @Getter private estadoHeladera estado;
  @Getter private List<Alerta> alertas;
  @Getter private List<Desperfecto> desperfectos;
  @Getter private Float tempMax;
  @Getter private Float tempMin;
  @Getter private Float ultimaTempRegistrada;


}
