package ar.edu.utn.frba.dds.service.broker;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.repositories.SensorRepository;
import ar.edu.utn.frba.dds.simeal.service.broker.CustomMessageReceptor;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomMessageReceptorTest {
  CustomMessageReceptor messageReceptor;


  @Test
  @DisplayName("Se recibe un mensaje del broker conteniendo la medicion y se llama al sensor para procesarla")
  public void mensajeRecibidoYProcesado() throws Exception {
    messageReceptor = new CustomMessageReceptor((SensorRepository) ServiceLocator.getRepository(SensorRepository.class));
    MqttMessage mqttMessage = mock(MqttMessage.class);
    when(mqttMessage.toString())
        .thenReturn("{\"nombreHeladera\":\"UTNMedrano\",\"medicion\":{\"tipoMedicion\":\"medicionMovimiento\",\"fechaHora\":\""+ LocalDateTime.now() + "\"}}");
    messageReceptor.messageArrived("heladera/medicion", mqttMessage);
    //TODO
  }
}
