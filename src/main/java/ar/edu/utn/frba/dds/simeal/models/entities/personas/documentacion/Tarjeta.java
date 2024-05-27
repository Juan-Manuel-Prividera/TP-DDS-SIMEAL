package ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion;

import ar.edu.utn.frba.dds.simeal.models.entities.Retiro;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.PersonaVulnerable;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;




@Getter
public class Tarjeta {
  private String codigo;
  @Setter
  private PersonaVulnerable personaVulnerable;
  private final List<LocalDate> fechasRetiros;
  private final int limiteDeUsoDiario = 4;
  private final int retirosAdicionalesPorMenores = 1;


  public  Tarjeta(String codigo, PersonaVulnerable personaVulnerable,
                  List<LocalDate> fechasRetiros) {
    this.codigo = codigo;
    this.personaVulnerable = personaVulnerable;
    this.fechasRetiros = fechasRetiros;
  }

  public boolean puedeRetirar() {
    return this.fechasRetiros.stream()
      .filter(f -> f.getDayOfYear() == LocalDate.now().getDayOfYear()).toList().size()
        <
      this.calcularLimiteDeUso();
  }

  public int calcularLimiteDeUso() {
    return limiteDeUsoDiario + this.personaVulnerable.cantHijosMenores();
  }

  public void agregarRetiro(Retiro retiro) {
    this.fechasRetiros.add(retiro.getFechaRetiro());
  }

}
