package com.example.indoorlocalizationcleancoders.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.indoorlocalizationcleancoders.FloorMapComposableWithObjects
import com.example.indoorlocalizationcleancoders.MqttHelper
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.indoorlocalizationcleancoders.data.models.TrackedObject

@Composable
fun HomePage(navController: NavController) {
    val context = LocalContext.current
    var trackedObjects by remember { mutableStateOf(emptyList<TrackedObject>()) }
    var activeFloormap by remember { mutableStateOf("assets/Tlocrt.png") }

    val mqttHelper = remember {
        MqttHelper(context) { newMessage ->
            println("Received new position: ${newMessage.AssetName} -> x: ${newMessage.X}, y: ${newMessage.Y}")

            trackedObjects = trackedObjects.toMutableList().apply {
                val index = indexOfFirst { it.AssetName == newMessage.AssetName }
                if (index != -1) {
                    this[index] = newMessage
                } else {
                    add(newMessage)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        mqttHelper.connect()
        mqttHelper.subscribe("floormap/active") { newFloormap ->
            activeFloormap = newFloormap
            // Kada promijenimo floormap, resetiramo listu objekata
            trackedObjects = emptyList()
        }
    }

    DisposableEffect(Unit) {
        onDispose { mqttHelper.disconnect() }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {
                activeFloormap = "assets/Tlocrt.png"
                trackedObjects = emptyList() // Resetiraj objekte na promjenu tlocrta
                mqttHelper.publishMessage("floormap/active", activeFloormap)
            }) { Text("Floormap 1") }

            Button(onClick = {
                activeFloormap = "assets/Tlocrt2.jpg"
                trackedObjects = emptyList()
                mqttHelper.publishMessage("floormap/active", activeFloormap)
            }) { Text("Floormap 2") }

            Button(onClick = {
                activeFloormap = "assets/Tlocrt3.png"
                trackedObjects = emptyList()
                mqttHelper.publishMessage("floormap/active", activeFloormap)
            }) { Text("Floormap 3") }
        }

        // Prikaz objekata samo za aktivni tlocrt
        FloorMapComposableWithObjects(
            trackedObjects = trackedObjects,
            activeFloormap = activeFloormap
        )
    }
}
