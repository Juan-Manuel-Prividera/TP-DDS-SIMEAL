package ar.edu.utn.frba.dds.simeal.service.broker;

import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.net.ssl.SSLSocketFactory;

public class MQTTSubscriber {
    public static void main(String[] args) {
        String broker = "ssl://50b513bcf4654e66b3f4c22d14623e5c.s1.eu.hivemq.cloud:8883";
        String topic = "heladera/medicion";
        String username = "simeal";
        String password = "simeal";
        String clientId = "JavaSubscriber";

        try {
            MqttClient client = new MqttClient(broker, clientId);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Logger.error("Conexión perdida! " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    // TODO: Parsear y loggear lo recibido
                    System.out.println("Message received:\nTopic: " + topic + "\nMessage: " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setSocketFactory(SSLSocketFactory.getDefault());

            client.connect(options);
            client.subscribe(topic);
            Logger.info("Conectado y suscripto al topic '" + topic+"'");

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
