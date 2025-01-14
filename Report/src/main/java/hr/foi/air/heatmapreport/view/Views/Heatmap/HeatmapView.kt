package hr.foi.air.heatmapreport.view.Views.Heatmap

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistoryGET

@Composable
fun HeatmapView(
    assetPositions: List<AssetPositionHistoryGET>,
    mapWidth: Double,
    mapHeight: Double,
    size:Int
) {
    val densityMap = mutableMapOf<Pair<Double, Double>, Int>()

    // Aggregate density for heatmap
    for (position in assetPositions) {
        val key = Pair(position.x, position.y)
        densityMap[key] = densityMap.getOrDefault(key, 0) + 1
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        densityMap.forEach { (position, count) ->
            val (x, y) = position

            // Scale positions based on the map dimensions
            val scaleX = (x / 1000f) * mapWidth
            val scaleY = (y / 1000f) * mapHeight

            // Determine the color for the current density
            val color = getColorForDensity(count)

            drawCircle(
                color = color,
                radius = 10f * count,
                center = Offset(scaleX.toFloat()*size, scaleY.toFloat()*size)
            )
        }
    }
}

// Determine the color based on density
fun getColorForDensity(density: Int): Color {
    return when {
        density > 10 -> Color.Red
        density > 5 -> Color.Yellow
        else -> Color.Green
    }
}