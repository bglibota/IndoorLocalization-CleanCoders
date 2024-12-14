package com.example.indoorlocalizationcleancoders.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.indoorlocalizationcleancoders.FloorMapComposableWithMovingObjects
import com.example.indoorlocalizationcleancoders.MqttHelper

@Composable
fun HomePage(navController: NavController) {
    val context = LocalContext.current
    var message by remember { mutableStateOf("Waiting for message...") }
    var objectPositions by remember { mutableStateOf(emptyList<Pair<Float, Float>>()) }
    var showObject by remember { mutableStateOf(false) } // Promjenjiva za prikazivanje objekta

    // Kreirajte MQTT pomoćnu klasu
    val mqttHelper = remember {
        MqttHelper(context) { newMessage ->
            message = newMessage  // Ažurirajte stanje s novom porukom
            println("Received message: $newMessage") // Dodaj log za praćenje poruka
            try {
                val parsedMessage = newMessage.split(",").map { it.toFloat() }
                if (parsedMessage.size == 2) {
                    objectPositions = listOf(Pair(parsedMessage[0], parsedMessage[1])) // Ažuriraj poziciju objekta
                    showObject = true // Omogući prikaz objekta kada poruka stigne
                }
            } catch (e: Exception) {
                // Upravite greške parsiranja, ako postoje
                e.printStackTrace()
            }
        }
    }

    // Povezivanje s MQTT brokerom
    LaunchedEffect(Unit) {
        mqttHelper.connect()  // Povežite se s brokerom čim je Composable prikazan
    }

    // Isključivanje MQTT veze prilikom izlaska iz Composable-a
    DisposableEffect(Unit) {
        onDispose {
            mqttHelper.disconnect()
        }
    }

    // Prikaz FloorMap s kretanjem objekta
    FloorMapComposableWithMovingObjects(
        objectPositions = objectPositions,
        showObject = showObject // Dodajte kontrolu vidljivosti objekta
    )
}
