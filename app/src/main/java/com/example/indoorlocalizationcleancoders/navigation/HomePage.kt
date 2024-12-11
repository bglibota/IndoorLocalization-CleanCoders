package com.example.indoorlocalizationcleancoders.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.indoorlocalizationcleancoders.MqttHelper

@Composable
fun HomePage(navController: NavController) {
    Text(text = "Welcome to Home Page!")
    val context = LocalContext.current
    var message by remember { mutableStateOf("Waiting for message...") }

    // Kreirajte MQTT pomoćnu klasu
    val mqttHelper = remember {
        MqttHelper(context) { newMessage ->
            message = newMessage  // Ažurirajte stanje s novom porukom
        }
    }

    // Povezivanje s MQTT brokerom
    LaunchedEffect(Unit) {
        mqttHelper.connect()  // Povežite se s brokerom čim je Composable prikazan
    }

    // Ispisivanje poruke
    Text(text = message)

    // Isključivanje MQTT veze prilikom izlaska iz Composable-a
    DisposableEffect(Unit) {
        onDispose {
            mqttHelper.disconnect()
        }
    }
}
