package com.example.indoorlocalizationcleancoders.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.indoorlocalizationcleancoders.MqttHelper
import com.example.indoorlocalizationcleancoders.R

@Composable
fun HeatmapPage() {
    var positions by remember { mutableStateOf(listOf<HeatmapData>()) }
    var selectedFloorMap by remember { mutableStateOf("Tlocrt1") }
    val context = LocalContext.current

    DisposableEffect(context) {
        val mqttHelper = MqttHelper(context) { trackedObject ->
            val data = HeatmapData(trackedObject.X.toInt(), trackedObject.Y.toInt())
            positions = positions + data
        }

        mqttHelper.connect()

        onDispose {
            mqttHelper.disconnect()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Heatmap Data", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        var expanded by remember { mutableStateOf(false) }
        val floorMaps = listOf("Tlocrt1", "Tlocrt2", "Tlocrt3")

        val interactionSource = remember { MutableInteractionSource() }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = selectedFloorMap,
                onValueChange = { },
                label = { Text("Select Floor Map") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Dropdown icon"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Regular DropdownMenu
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                floorMaps.forEach { floor ->
                    DropdownMenuItem(
                        text = { Text(text = floor) },
                        onClick = {
                            selectedFloorMap = floor
                            expanded = false
                        },
                        interactionSource = interactionSource,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val floorMapResId = when (selectedFloorMap) {
            "Tlocrt1" -> R.drawable.tlocrt
            "Tlocrt2" -> R.drawable.tlocrt2
            "Tlocrt3" -> R.drawable.tlocrt3
            else -> R.drawable.tlocrt
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = floorMapResId),
                contentDescription = "Floor Map",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                contentScale = ContentScale.FillBounds
            )

            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val intensityMap = mutableMapOf<Pair<Float, Float>, Int>()

                positions.forEach { position ->
                    val normalizedX = (position.x.toFloat() / 100) * canvasWidth
                    val normalizedY = (position.y.toFloat() / 100) * canvasHeight
                    val key = Pair(normalizedX, normalizedY)
                    intensityMap[key] = intensityMap.getOrDefault(key, 0) + 1
                }

                val maxIntensity = intensityMap.values.maxOrNull() ?: 1
                val normalizationFactor = maxIntensity.coerceAtMost(10)

                intensityMap.forEach { (position, intensity) ->
                    val normalizedIntensity = intensity.toFloat() / normalizationFactor
                    val color = when {
                        normalizedIntensity > 0.7f -> Color.Red
                        normalizedIntensity > 0.4f -> Color.Yellow
                        else -> Color.Green
                    }

                    drawCircle(
                        color = color.copy(alpha = 0.7f),
                        radius = 10f,
                        center = Offset(position.first, position.second)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

data class HeatmapData(val x: Int, val y: Int, val intensity: Float = 0f)