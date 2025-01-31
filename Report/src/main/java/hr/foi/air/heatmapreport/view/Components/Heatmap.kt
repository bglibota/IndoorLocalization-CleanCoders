package hr.foi.air.heatmapreport.view.Components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistoryGET
import kotlin.math.abs
import kotlin.math.pow


@Composable
fun HeatmapView(
    assetPositions: List<AssetPositionHistoryGET>,
    heatmapWidth: Double = 900.0,
    heatmapHeight: Double = 1200.0,
    offsetX: Double = 67.0,
    offsetY: Double = 40.0
) {


    Box(modifier = Modifier.size(450.dp)) {
        Canvas(modifier = Modifier
            .size(150.dp)

        ) {
            assetPositions.forEach { position ->
                val scaleX = (position.x / 100.0) * heatmapWidth+offsetX
                val scaleY = (position.y / 100.0) * heatmapHeight+offsetY

                val count = assetPositions.count {
                    abs(it.x - position.x) <= 6 && abs(it.y - position.y) <= 6
                }
                val baseRadius = 10f
                val scaleFactor = 2.5f
                val color = getColorForDensity(count)
                drawCircle(
                    color = color,
                    radius = baseRadius + (count.toFloat().pow(0.7f) * scaleFactor),
                    center = Offset(scaleX.toFloat(), scaleY.toFloat())
                )
            }
        }


    }
}

fun getColorForDensity(density: Int): Color {
    return when {
        density >= 15 -> Color.Red
        density >= 10 -> Color(1f, 0.65f, 0f)
        density >= 5 -> Color.Yellow
        else -> Color.Green
    }
}
