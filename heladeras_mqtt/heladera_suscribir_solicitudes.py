#!/bin/python
import paho.mqtt.client as mqtt
import sys

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


# Callback for when the client connects to the broker
def on_connect(client, userdata, flags, rc):
    if rc == 0:
        client.subscribe(topic)
        print(f"Conectado al broker en el topic '{topic}' :o")
    else:
        print("No se pudo conectar al broker :( - ", rc)

# Callback for handling incoming messages
def on_message(client, userdata, msg):
    print(f"Solicitud recibida : '{msg.payload.decode()}'")

# Set up the MQTT client
client = mqtt.Client(heladera_id)
client.username_pw_set(username, password)
client.tls_set(tls_version=mqtt.ssl.PROTOCOL_TLS)
client.on_connect = on_connect
client.on_message = on_message  # Set the on_message callback to handle incoming messages

# Connect to the broker
client.connect(broker_address, broker_port, 60)

# Start the MQTT client loop, which will handle both publishing and subscribing
client.loop_forever()

