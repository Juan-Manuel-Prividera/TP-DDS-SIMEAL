package ar.edu.utn.frba.dds.simeal.service.broker;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;
import ar.edu.utn.frba.dds.simeal.models.repositories.SensorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class CustomMessageReceptor implements IMqttMessageListener {
  private SensorRepository sensorRepository;

  public CustomMessageReceptor(SensorRepository sensorRepository) {
    this.sensorRepository = sensorRepository;
  }

  @Override
  public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
    try {
      System.out.println("Message received: " + mqttMessage.toString());
      //ejemplo json:
      //String content = "{\"nombreHeladera\":\"martin\",\"medicion\":{\"tipoMedicion\":\"medicionMovimiento\",\"fechaHora\":\""+ LocalDateTime.now() + "\"}}";
      String json = mqttMessage.toString();
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      WrapperMedicion wrapperMedicion = objectMapper.readValue(json, WrapperMedicion.class);
      Sensor sensor = this.sensorRepository.buscarSegun(wrapperMedicion.getNombreHeladera());
      sensor.recibir(wrapperMedicion.getMedicion());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
