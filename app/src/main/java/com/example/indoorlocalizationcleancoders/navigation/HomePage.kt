package com.example.indoorlocalizationcleancoders.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.indoorlocalizationcleancoders.FloorMapComposableWithObjects
import com.example.indoorlocalizationcleancoders.MqttHelper
import com.example.indoorlocalizationcleancoders.TrackedObject

@Composable
fun HomePage(navController: NavController) {
    val context = LocalContext.current
    var trackedObjects by remember { mutableStateOf(emptyList<TrackedObject>()) }

    // Kreirajte MQTT pomoćnu klasu
    val mqttHelper = remember {
        MqttHelper(context) { newMessage ->
            println("Received new position: ${newMessage.AssetName} -> x: ${newMessage.X}, y: ${newMessage.Y}")

            // Ažuriraj listu objekata unutar glavne niti
            trackedObjects = trackedObjects.toMutableList().apply {
                val index = indexOfFirst { it.AssetName == newMessage.AssetName }
                if (index != -1) {
                    this[index] = newMessage  // Ažuriraj postojeći objekt
                } else {
                    add(newMessage)  // Dodaj novi objekt
                }
            }
        }
    }

    // Povezivanje s MQTT brokerom
    LaunchedEffect(Unit) {
        mqttHelper.connect()
    }

    // Isključivanje MQTT veze prilikom izlaska iz Composable-a
    DisposableEffect(Unit) {
        onDispose {
            mqttHelper.disconnect()
        }
    }

    println("Tracked Objects: $trackedObjects")
    // Prikaz tlocrta s objektima
    FloorMapComposableWithObjects(
        trackedObjects = trackedObjects
    )
}
