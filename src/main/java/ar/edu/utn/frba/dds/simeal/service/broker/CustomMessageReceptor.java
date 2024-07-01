package ar.edu.utn.frba.dds.simeal.service.broker;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.sensor.Sensor;
import ar.edu.utn.frba.dds.simeal.models.repositories.SensorRepository;
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
    System.out.println("Message received: " + mqttMessage.toString());
    String json = mqttMessage.toString();
    Gson gson = new Gson();
    WrapperMedicion wrapperMedicion = gson.fromJson(json, WrapperMedicion.class);
    Sensor sensor = this.sensorRepository.buscarSegun(wrapperMedicion.getNombreHeladera());
    sensor.recibir(wrapperMedicion.getMedicion());
  }
}
