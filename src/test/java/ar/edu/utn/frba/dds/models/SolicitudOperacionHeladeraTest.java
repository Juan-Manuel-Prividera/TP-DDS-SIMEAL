package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.SolicitudOperacionHeladera;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class SolicitudOperacionHeladeraTest {
  private Heladera heladera;
  private SolicitudOperacionHeladera solicitudEnHora, solicitudFueraDeHora;
  @BeforeEach
  public void init(){
    this.heladera = new Heladera();
    this.solicitudFueraDeHora = SolicitudOperacionHeladera.builder().horaInicio(LocalDateTime.now().minusHours(10)).heladera(this.heladera).build();
    this.solicitudEnHora = SolicitudOperacionHeladera.builder().horaInicio(LocalDateTime.now().plusSeconds(1)).heladera(this.heladera).build();
  }
  @Test @DisplayName("Solicitud con heladera correcta, pero fuera de hora => solicitud.puedeEjecutarse() == False")
  public void heladeraCorrectaHoraIncorrecta(){
    Assertions.assertFalse(this.solicitudFueraDeHora.puedeEjecutarse(this.heladera));
  }
  @Test @DisplayName("Solicitud con heladera correcta y en hora correcta => solicitud.puedeEjecutarse() == True")
  public void heladeraCorrectaHoraCorrecta(){
    Assertions.assertTrue(this.solicitudEnHora.puedeEjecutarse(this.heladera));
  }

}
