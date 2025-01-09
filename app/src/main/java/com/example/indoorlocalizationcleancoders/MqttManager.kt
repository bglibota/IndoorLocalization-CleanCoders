package com.example.indoorlocalizationcleancoders

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

class MqttHelper(
    context: Context,
    private val messageListener: (TrackedObject) -> Unit
) {
    private val mqttClient: MqttClient
    private val mqttOptions: MqttConnectOptions
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter = moshi.adapter(TrackedObject::class.java)

    init {
        val serverUri = "tcp://10.0.2.2:1883" //"tcp://localhost:1883"
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
            mqttClient.subscribe("object/position", 0)

            mqttClient.setCallback(object : MqttCallback {
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    message?.let {
                        val messageString = it.toString()
                        println("Received message: $messageString")

                        try {
                            val trackedObject = jsonAdapter.fromJson(messageString)
                            trackedObject?.let { obj -> messageListener(obj) }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun connectionLost(cause: Throwable?) {
                    println("Connection lost: ${cause?.message}")
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {}
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
