package ar.edu.utn.frba.dds.simeal.service.broker;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TarjetaColaborador.SolicitudOperacionHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TarjetaColaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TarjetaColaborador.TipoOperacion;
import ar.edu.utn.frba.dds.simeal.service.ConfigReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublisher {

  ConfigReader configReader = new ConfigReader();

  public void conectar(SolicitudOperacionHeladera solicitudOperacion, String topic) {

    //ejemplo topico: "test/topic"
    String broker = configReader.getProperty("broker.host");
    String clientId = "JavaPublisher";
    MemoryPersistence persistence = new MemoryPersistence();

    try {
      //conexion con el broker
      MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
      MqttConnectOptions connOpts = new MqttConnectOptions();
      connOpts.setCleanSession(true);
      System.out.println("Connecting to broker: " + broker);
      sampleClient.connect(connOpts);
      System.out.println("Connected");

      //llega solicitud por parametro
      //parsear solicitudOperacion a json
      ObjectMapper objectMapper = new ObjectMapper();
      String content = objectMapper.writeValueAsString(solicitudOperacion);

      //se crea el mensaje y se publica
      int qos = 2;
      MqttMessage message = new MqttMessage(content.getBytes());
      message.setQos(qos);
      System.out.println("Publishing message: " + content);
      sampleClient.publish(topic, message);
      System.out.println("Message published");

      sampleClient.disconnect();
      System.out.println("Disconnected");
      System.exit(0);
    } catch (MqttException me) {
      System.out.println("reason " + me.getReasonCode());
      System.out.println("msg " + me.getMessage());
      System.out.println("loc " + me.getLocalizedMessage());
      System.out.println("cause " + me.getCause());
      System.out.println("excep " + me);
      me.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
