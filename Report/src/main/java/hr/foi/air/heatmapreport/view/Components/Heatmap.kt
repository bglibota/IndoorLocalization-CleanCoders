package hr.foi.air.heatmapreport.view.Components

import android.util.Log.d
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
) {
    Canvas(modifier = Modifier
            .fillMaxSize()

        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val offsetX=canvasWidth*0.025
            val offsetY=canvasHeight*0.025
            val scaleFactor = 1.08f

            d("HeatmapViewDims", "Canvas Width: $canvasWidth, Canvas Height: $canvasHeight")
            assetPositions.forEach { position ->


                val count = assetPositions.count {
                    abs(it.x - position.x) <= 6 && abs(it.y - position.y) <= 6
                }
                val scaleX = (position.x / 100.0) * canvasWidth/scaleFactor+offsetX
                val scaleY = (position.y / 100.0) * canvasHeight/scaleFactor+offsetY
                val baseRadius = 10f
                val color = getColorForDensity(count)
                drawCircle(
                    color = color,
                    radius = baseRadius + (count.toFloat().pow(0.7f) * scaleFactor),
                    center = Offset(scaleX.toFloat(), scaleY.toFloat())
                )
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
