package ar.edu.utn.frba.dds.domain.colaboracion;

import ar.edu.utn.frba.dds.domain.heladera.Heladera;
import ar.edu.utn.frba.dds.domain.ubicacion.Ubicacion;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdherirHeladera implements Colaboracion{

  private final Ubicacion ubicacion;
  private LocalDate fechaColocacion = LocalDate.now();
  private List<Ubicacion> ubicacionesRecomendadas; //Es necesario generarlo con la lista?
  private Float factorDeReconocimiento = 5F;

  @Override
  public void colaborar(){
    Heladera heladera = new Heladera(ubicacion, fechaColocacion);
  }

  @Override
  public Float calcularReconocimientoParcial() {
    Period periodoEnFuncionamiento = Period.between(this.fechaColocacion, LocalDate.now());

    Integer cantMeses =
        periodoEnFuncionamiento.getYears()*12
          +
        periodoEnFuncionamiento.getMonths();
    return cantMeses.floatValue() * this.factorDeReconocimiento;
  }
}
