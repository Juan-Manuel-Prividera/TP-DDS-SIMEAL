package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TarjetaColaborador.OperacionSobreHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TarjetaColaborador.TipoOperacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class OperacionSobreHeladeraTest {
  private Heladera heladeraCorrecta = new Heladera(), heladeraIncorrecta = new Heladera();
  private OperacionSobreHeladera operacionEnRango = new OperacionSobreHeladera(TipoOperacion.RETIRO, this.heladeraCorrecta, 3, LocalDateTime.now());
  private OperacionSobreHeladera operacionFueraDeRago = new OperacionSobreHeladera(TipoOperacion.RETIRO, this.heladeraCorrecta, 3, LocalDateTime.now().minusHours(5));
  @BeforeEach
  public void init(){
    this.heladeraCorrecta.setNombre("Albion Online");
    this.heladeraIncorrecta.setNombre("Wow");
  }
  @Test @DisplayName("Operacion contrastada con heladera incorrecta y horario dentro de rango permitido => puedeEjecutarse == false")
  public void heladeraIncorrectaTest(){
    Assertions.assertFalse(operacionEnRango.puedeEjecutarse(this.heladeraIncorrecta));
  }

  @Test @DisplayName("Operacion contrastada con heladera correcta y horario fuera de rengo premitido => puedeEjecutarse == false")
  public void operacionFueraDeRangoTest(){
    Assertions.assertFalse(operacionFueraDeRago.puedeEjecutarse(this.heladeraCorrecta));
  }

  @Test@DisplayName("Operacion contrastada con heladera correcta y dentro del rango horario permitido => puedeEjecutarse == true")
  public void operacionEnRangoTest(){
    Assertions.assertTrue(operacionEnRango.puedeEjecutarse(this.heladeraCorrecta));
  }
}
