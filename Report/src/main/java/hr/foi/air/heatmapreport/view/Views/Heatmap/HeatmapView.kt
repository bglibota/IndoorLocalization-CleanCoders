package hr.foi.air.heatmapreport.view.Views.Heatmap

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistoryGET
import kotlin.math.abs


@Composable
fun HeatmapView(
    assetPositions: List<AssetPositionHistoryGET>,
    heatmapWidth: Double = 996.0,
    heatmapHeight: Double = 1307.0,
    offsetX: Double = 67.0,
    offsetY: Double = 40.0
) {


    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier
            .fillMaxSize()

        ) {
            assetPositions.forEach { position ->
                val scaleX = (position.x / 100.0) * heatmapWidth + offsetX
                val scaleY = (position.y / 100.0) * heatmapHeight + offsetY

                val count = assetPositions.count {
                    abs(it.x - position.x) <= 10 && abs(it.y - position.y) <= 10
                }

                val color = getColorForDensity(count)
                drawCircle(
                    color = color,
                    radius = 8f * count,
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
