package ar.edu.utn.frba.dds.simeal.service.broker;

import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.TipoRepo;
import ar.edu.utn.frba.dds.simeal.service.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttSubscriber {

  ConfigReader configReader = new ConfigReader();

  public void conectar(String topic) {

    //ejemplo topico: "test/topic"
    String broker = configReader.getProperty("broker.host");
    String clientId = "JavaSubscriber";
    MemoryPersistence persistence = new MemoryPersistence();
    Repositorio sensorRepository = ServiceLocator.getRepository(TipoRepo.SENSOR);

    try {
      //conexion con el broker
      MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
      MqttConnectOptions connOpts = new MqttConnectOptions();
      connOpts.setCleanSession(true);
      System.out.println("Connecting to broker: " + broker);
      sampleClient.connect(connOpts);
      System.out.println("Connected");

      //Creo el receptor
      CustomMessageReceptor receptor = new CustomMessageReceptor(sensorRepository);

      //subscribo a el topico con el receptor
      sampleClient.subscribe(topic, receptor);
      System.out.println("Subscribed to topic: " + topic);

    } catch (MqttException me) {
      System.out.println("reason " + me.getReasonCode());
      System.out.println("msg " + me.getMessage());
      System.out.println("loc " + me.getLocalizedMessage());
      System.out.println("cause " + me.getCause());
      System.out.println("excep " + me);
      me.printStackTrace();
    }
  }
}
