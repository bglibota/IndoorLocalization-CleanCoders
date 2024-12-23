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
        val serverUri = "tcp://localhost:1883"  // Zamijenite s vašim MQTT broker URI-jem
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
                        println("Received message: $messageString")  // Logiraj primljenu poruku

                        // Pretvorite JSON u objekt koristeći Moshi
                        try {
                            val trackedObject = jsonAdapter.fromJson(messageString)
                            trackedObject?.let { obj -> messageListener(obj) }  // Pozovi callback s objektom
                        } catch (e: Exception) {
                            e.printStackTrace()  // Upravite greške pri parsiranju
                        }
                    }
                }

                override fun connectionLost(cause: Throwable?) {
                    // Upravite gubitak veze
                    println("Connection lost: ${cause?.message}")
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
