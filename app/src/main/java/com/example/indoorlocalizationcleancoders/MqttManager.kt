package com.example.indoorlocalizationcleancoders
import android.content.Context
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import android.util.Log


    class MqttHelper(context: Context, private val messageListener: (String) -> Unit) {

    private val mqttClient: MqttClient
    private val mqttOptions: MqttConnectOptions

    init {
        val serverUri = "tcp://10.0.2.2:1883"  // Zamijenite s vašim MQTT broker URI-jem
        val clientId = MqttClient.generateClientId()
        mqttClient = MqttClient(serverUri, clientId, MemoryPersistence())

        mqttOptions = MqttConnectOptions().apply {
            isCleanSession = true
            connectionTimeout = 10
            keepAliveInterval = 30
        }
    }

    fun connect() {
        try {
            mqttClient.connect(mqttOptions)
            mqttClient.subscribe("your/topic", 0)

            mqttClient.setCallback(object : MqttCallback {
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    message?.let {
                        Log.d("MQTT", "Received message: ${it.toString()} on topic: $topic")
                        messageListener(it.toString())
                    }
                }

                override fun connectionLost(cause: Throwable?) {
                    // Upravite gubitak veze
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    // Upravite završetak dostave
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        try {
            mqttClient.disconnect()
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}