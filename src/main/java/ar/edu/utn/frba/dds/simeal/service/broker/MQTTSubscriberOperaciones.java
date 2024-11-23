package ar.edu.utn.frba.dds.simeal.service.broker;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.controllers.heladera.OperacionHeladeraController;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.TipoOperacion;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import org.eclipse.paho.client.mqttv3.*;

public class MQTTSubscriberOperaciones {
  private static final ConfigReader configReader = new ConfigReader();
  public static void suscribe()  {
    String broker = configReader.getProperty("broker");
    String username = configReader.getProperty("broker.user");
    String password = configReader.getProperty("broker.pass");
    String topic = "heladera/operacion";
    String clientId = "JavaSubscriber-Operaciones";

    //CountDownLatch latch = new CountDownLatch(1);

    try {
      MqttClient client = new MqttClient(broker, clientId);

      MqttConnectOptions options = new MqttConnectOptions();
      options.setUserName(username);
      options.setPassword(password.toCharArray());
      options.setAutomaticReconnect(true); // Activa la reconexión automática
      options.setCleanSession(true);
      options.setKeepAliveInterval(5); // En segundos, ajusta según sea necesario

      client.connect(options);
      client.subscribe(topic);
      Logger.info("Conectado y suscripto al topic '" + topic+"'");

      client.setCallback(new MqttCallback() {
      @Override
      public void connectionLost(Throwable cause) {
        Logger.error("Conexión perdida! " + cause.getMessage());
        try {
          while (!client.isConnected()) {
            Logger.info("Intentando reconectar...");
            client.connect(options);
            client.subscribe(topic);
            Logger.info("Reconectado con éxito.");
          }
        } catch (MqttException e) {
          Logger.error("Error al intentar reconectar: " + e.getMessage());
        }
      }

      @Override
      public void messageArrived(String topic, MqttMessage message) {
        Logger.debug("Message received:\nTopic: " + topic + "\nMessage: " + new String(message.getPayload()));
        String[] partes = message.toString().split(" ");
        String heladeraId = partes[0].split(":")[1].replace(" ", "");
        String tipoOperacion = partes[1].replace(" ", "");
        String tarjetaColaboradorId = partes[2].replace(" ", "");
        ServiceLocator.getController(OperacionHeladeraController.class)
          .create(Long.parseLong(heladeraId), TipoOperacion.valueOf(tipoOperacion), Long.parseLong(tarjetaColaboradorId));
      }

      @Override
      public void deliveryComplete(IMqttDeliveryToken token) {
        // No hacemos nada.
      }

      });

    //  latch.await();
      } catch (Exception e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
      }

  }
}
