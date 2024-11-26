package ar.edu.utn.frba.dds.simeal.server;

import ar.edu.utn.frba.dds.simeal.service.broker.MQTTSubscriberMediciones;
import ar.edu.utn.frba.dds.simeal.service.broker.MQTTSubscriberOperaciones;

public class App {

  public static void main(String[] args) {
    Server.init();
//    new Thread(() -> MQTTSubscriberOperaciones.suscribe()).start();
//    new Thread(() -> MQTTSubscriberMediciones.suscribe()).start();
  }
}
