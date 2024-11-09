package ar.edu.utn.frba.dds.simeal.service.broker;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.SolicitudOperacionHeladera;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import org.eclipse.paho.client.mqttv3.*;

public class MQTTPublisherSolicitudes {
  // TODO: Testear esto... confio en que anda igual
  private static final ConfigReader configReader = new ConfigReader();
  public static void realizarSolicitud(SolicitudOperacionHeladera solicitudOperacionHeladera) {
    String broker = configReader.getProperty("broker");
    String username = configReader.getProperty("broker.user");
    String password = configReader.getProperty("broker.pass");
    String topic = "heladera/solicitud/" + solicitudOperacionHeladera.getHeladera().getId() ;
    String clientId = "JavaSubscriber";
    Logger.debug("Se intenta mandar solicitud a heladera");
    try {
      // Crear el cliente MQTT
      MqttClient client = new MqttClient(broker, MqttClient.generateClientId());

      // Configurar las opciones de conexión
      MqttConnectOptions options = new MqttConnectOptions();
      options.setUserName(username);
      options.setPassword(password.toCharArray());

      // Configurar el callback para cuando el cliente se conecta
      client.setCallback(new MqttCallback() {
        @Override
        public void connectionLost(Throwable cause) {
          System.err.println("Conexión perdida: " + cause.getMessage());
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) {
          // No recibe mensajes por aca
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
          // No es necesario para el publisher, pero se requiere por la interfaz
        }
      });

      // Conectar al broker
      client.connect(options);
      System.out.println("Conectado al broker en el topic '" + topic + "'");

      // Suscribirse al topic
      client.subscribe(topic);
      System.out.println("Suscripto al topic: " + topic);

      // Publicar un mensaje
      String payload = "Tipo solicitud: " + solicitudOperacionHeladera.getTipoOperacion() +
        " Tarjeta de colaborador: " + solicitudOperacionHeladera.getTarjetaColaborador().getId() +
        " Hora limite: " + solicitudOperacionHeladera.getHoraDeRealizacion();

      MqttMessage message = new MqttMessage(payload.getBytes());
      message.setQos(1); // Quality of Service (QoS)
      client.publish(topic, message);
      System.out.println("Mensaje publicado: '" + payload + "'");

    } catch (MqttException e) {
      System.err.println("Error al conectar o publicar: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
