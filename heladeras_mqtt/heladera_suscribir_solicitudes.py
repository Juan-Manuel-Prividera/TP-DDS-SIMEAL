#!/bin/python
import paho.mqtt.client as mqtt
import sys
import signal

# -- Este script publica y suscribe operaciones en la heladera --
if len(sys.argv) < 2:
    print(f"Usage: {sys.argv[0]} <heladera_id>")
    exit(1)


heladera_id = sys.argv[1]

# Constants
broker_address = "50b513bcf4654e66b3f4c22d14623e5c.s1.eu.hivemq.cloud"
broker_port = 8883
topic = "heladera/solicitud/"+heladera_id
# Todo: censurar
username = "simeal"
password = "simeal"


def on_connect(client, userdata, flags, rc):
    if rc == 0:
        client.subscribe(topic)
        print(f"Conectado al broker en el topic '{topic}' :o")
    else:
        print("No se pudo conectar al broker :( - ", rc)

def on_message(client, userdata, msg):
    print(f"Solicitud recibida : '{msg.payload.decode()}'")

def leave_gracefully(sig, frame):
    client.loop_stop()  # Stop the MQTT loop
    client.disconnect()  # Disconnect from the broker
    sys.exit(0)

def leave_gracefully(sig, frame):
    client.loop_stop()  # Stop the MQTT loop
    client.disconnect()  # Disconnect from the broker
    sys.exit(0)

client = mqtt.Client(heladera_id)
client.username_pw_set(username, password)
client.tls_set(tls_version=mqtt.ssl.PROTOCOL_TLS)
client.on_connect = on_connect
client.on_message = on_message  # Set the on_message callback to handle incoming messages

client.connect(broker_address, broker_port, 60)


signal.signal(signal.SIGINT, leave_gracefully)
client.loop_forever()

