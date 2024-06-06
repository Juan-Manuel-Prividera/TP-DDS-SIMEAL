package ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion;

import ar.edu.utn.frba.dds.simeal.models.entities.Retiro;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.PersonaVulnerable;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;




@Getter
public class Tarjeta {
  private String codigo;
  private final PersonaVulnerable personaVulnerable;
  private final int limiteDeUsoDiario = 4;
  private final int retirosAdicionalesPorMenores = 1;


  public Tarjeta(String codigo, PersonaVulnerable personaVulnerable){
    this.codigo = codigo;
    this.personaVulnerable = personaVulnerable;
  }

  // TODO: Alguien tiene que pedir a la BD todos los retiros cargados.
  public boolean puedeRetirar(List<Retiro> retirosRealizados) {

    int cantidadRetirosRealizadosHoy = retirosRealizados.stream()
        .filter(r ->
            (r.getFecha().getYear() == LocalDate.now().getYear()
                && r.getFecha().getDayOfYear() == LocalDate.now().getDayOfYear())
        ).toList().size();

    return cantidadRetirosRealizadosHoy < this.calcularLimiteDeUso();

  }

  public int calcularLimiteDeUso() {
    return limiteDeUsoDiario + this.personaVulnerable.cantHijosMenores();
  }

}
