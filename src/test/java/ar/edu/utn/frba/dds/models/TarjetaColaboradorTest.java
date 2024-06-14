package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TarjetaColaborador.OperacionSobreHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TarjetaColaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TarjetaColaborador.TipoOperacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TarjetaColaboradorTest {
  private TarjetaColaborador tarjeta = new TarjetaColaborador();
  private Heladera heladeraCorrecta = new Heladera(), heladeraIncorrecta = new Heladera();
  private OperacionSobreHeladera operacion = new OperacionSobreHeladera(TipoOperacion.RETIRO, this.heladeraCorrecta, 3, LocalDateTime.now());
  @BeforeEach
  public void init(){
    this.tarjeta.addOperacion(this.operacion);
  }
  @Test @DisplayName("Tarjeta no posee una operación a realizar en la heladera => puedeAcceder == flase")
  public void TarjetaSinOperacionEnHeladera(){
    Assertions.assertFalse(this.tarjeta.puedeAcceder(this.heladeraIncorrecta));
  }

  @Test @DisplayName("Tarjeta posee una operación a realizar en la heladera => puedeAcceder == true")
  public void TarjetaConOperacionEnHeladera(){
    Assertions.assertTrue(this.tarjeta.puedeAcceder(this.heladeraCorrecta));
  }
}
