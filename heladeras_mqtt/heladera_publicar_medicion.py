#!/bin/python
import paho.mqtt.client as mqtt
import sys

# -- Este script publica mediciones de la heladera --

# Constants
broker_address = "50b513bcf4654e66b3f4c22d14623e5c.s1.eu.hivemq.cloud"
broker_port = 8883
topic = "heladera/medicion"
# Todo: censurar
username = "simeal"
password = "simeal"

if len(sys.argv) < 3:
    print(f"Usage: {sys.argv[0]} <heladera_id> <tipo_medicion> <medicion>")
    exit(1)

heladera_id = sys.argv[1]
tipo_medicion = sys.argv[2]
if len(sys.argv) >= 4:
    medicion = sys.argv[3]
else:
    medicion = ""

def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print("Conectado al broker :o")
        send_message(client)
    else:
        print("No se pudo conectar al broker :( - ", rc)

def send_message(client):
    payload = f"id:{heladera_id} {tipo_medicion} {medicion}"
    client.publish(topic, payload)
    print(f"Mensaje enviado: '{payload}'")
    exit(0)


client = mqtt.Client(heladera_id)
client.username_pw_set(username, password)
client.tls_set(tls_version=mqtt.ssl.PROTOCOL_TLS)
client.on_connect = on_connect

client.connect(broker_address, broker_port, 60)

client.loop_forever()

