package com.example.indoorlocalizationcleancoders.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.tlocrt),
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
