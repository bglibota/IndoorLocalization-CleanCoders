package com.example.indoorlocalizationcleancoders.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun HeatmapViewLive(
    assetPositions: List<HeatmapData>,
    mapWidth: Double,
    mapHeight: Double,
    size: Int
) {
    val densityMap = mutableMapOf<Pair<Double, Double>, Int>()

    for (position in assetPositions) {
        val key = Pair(position.x.toDouble(), position.y.toDouble())
        densityMap[key] = densityMap.getOrDefault(key, 0) + 1
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        densityMap.forEach { (position, count) ->
            val (x, y) = position

            val scaleX = (x / 100) * mapWidth
            val scaleY = (y / 100) * mapHeight

            val color = getColorForDensity(count)

            drawCircle(
                color = color,
                radius = 15f * count * size,
                center = Offset(scaleX.toFloat(), scaleY.toFloat())
            )
        }
    }
}

fun getColorForDensity(density: Int): Color {
    return when {
        density > 10 -> Color.Red
        density > 5 -> Color.Yellow
        else -> Color.Green
    }
}
