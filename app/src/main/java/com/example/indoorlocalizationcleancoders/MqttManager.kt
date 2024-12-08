package com.example.indoorlocalizationcleancoders

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MqttManager(private val context: Context) {

    private val serverUri = "tcp://test.mosquitto.org:1883" // testni URL
    private val clientId = "AndroidClient_" + System.currentTimeMillis()
    private lateinit var mqttClient: MqttAndroidClient

    fun connect(topic: String, onMessageReceived: (String) -> Unit) {
        mqttClient = MqttAndroidClient(context, serverUri, clientId)

        val options = MqttConnectOptions().apply {
            isCleanSession = true
        }

        mqttClient.connect(options, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("MQTT", "Connected to MQTT broker")
                subscribeToTopic(topic, onMessageReceived)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.e("MQTT", "Failed to connect: ${exception?.message}")
            }
        })
    }

    private fun subscribeToTopic(topic: String, onMessageReceived: (String) -> Unit) {
        mqttClient.subscribe(topic, 0, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("MQTT", "Subscribed to topic: $topic")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.e("MQTT", "Failed to subscribe: ${exception?.message}")
            }
        })

        mqttClient.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                val payload = message?.toString() ?: ""
                Log.d("MQTT", "Poruka stigla na topicu: $topic, s payloadom: $payload")
                onMessageReceived(payload)
            }

            override fun connectionLost(cause: Throwable?) {
                // Ispisuje poruku o grešci ako je veza izgubljena
                Log.e("MQTT", "Veza izgubljena: ${cause?.message}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                // Ispisuje kada je poruka isporučena
                Log.d("MQTT", "Isporuka poruke dovršena: ${token?.message?.toString()}")
            }
        })

    }

    fun disconnect() {
        mqttClient.disconnect(null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("MQTT", "Disconnected from MQTT broker")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.e("MQTT", "Failed to disconnect: ${exception?.message}")
            }
        })
    }
}
