#!/bin/python
import paho.mqtt.client as mqtt
import random
import signal


# -- Este script simula una heladera mandando su temperatura --


broker_address = "50b513bcf4654e66b3f4c22d14623e5c.s1.eu.hivemq.cloud"
broker_port = 8883
topic = "heladera/medicion"

# Todo: censurar
username = "simeal"
password = "simeal"

heladera_id = "1234"
temperatura = random.gauss(4.5, .75)
medicion = f"{temperatura:.3}"
tipo_medicion = "TEMPERATURA"



def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print("Connected to broker")
        send_message(client)
    else:
        print("Connection failed with code", rc)

def send_message(client):
    payload = f"id:{heladera_id} {tipo_medicion} {medicion}"
    client.publish(topic, payload)
    print(f"Sent message: {payload}")

client = mqtt.Client(heladera_id)
client.username_pw_set(username, password)
client.tls_set(tls_version=mqtt.ssl.PROTOCOL_TLS)
client.on_connect = on_connect

client.connect(broker_address, broker_port, 60)

signal.signal(signal.SIGINT, exit(0)) 
client.loop_forever()




