package ar.edu.utn.frba.dds.domain.colaboracion;

import ar.edu.utn.frba.dds.domain.heladera.Heladera;
import ar.edu.utn.frba.dds.domain.personas.Colaborador;
import ar.edu.utn.frba.dds.domain.ubicacion.Ubicacion;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdherirHeladera implements Colaboracion{
  private Colaborador colaborador;
  private LocalDate fechaDeRealizacion;
  private List<Ubicacion> ubicacionesRecomendadas; //Es necesario generarlo con la lista?
  private Float factorDeReconocimiento = 5F;
  private Heladera heladera;

/*
  public AdherirHeladera(Colaborador colaborador, LocalDate fechaDeRealizacion) {
    this.colaborador = colaborador;
    this.fechaDeRealizacion = fechaDeRealizacion;
  }
*/

  @Override
  public void colaborar(){
  //  Heladera heladera = new Heladera();
  }

  @Override
  public Float calcularReconocimientoParcial() {
    Period periodoEnFuncionamiento = Period.between(this.fechaDeRealizacion, LocalDate.now());

    Integer cantMeses =
        periodoEnFuncionamiento.getYears()*12
          +
        periodoEnFuncionamiento.getMonths();
    return cantMeses.floatValue() * this.factorDeReconocimiento;
  }
}
