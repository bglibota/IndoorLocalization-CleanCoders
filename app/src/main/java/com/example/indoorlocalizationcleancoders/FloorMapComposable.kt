package com.example.indoorlocalizationcleancoders

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.indoorlocalizationcleancoders.R

data class TrackedObject(
    val id: String,
    val x: Float,
    val y: Float
)

@Composable
fun FloorMapComposableWithObjects(
    modifier: Modifier = Modifier,
    trackedObjects: List<TrackedObject>
) {
    val configuration = LocalConfiguration.current
    val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
    val screenHeight = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }

    Box(modifier = modifier.fillMaxSize()) {

        // Prikaz tlocrta
        Image(
            painter = painterResource(id = R.drawable.tlocrt),
            contentDescription = "Floor Map",
            contentScale = ContentScale.FillBounds,
            modifier = modifier.fillMaxSize()
        )

        // Crtanje objekata na tlocrtnu mapu
        Canvas(modifier = modifier.fillMaxSize()) {
            trackedObjects.forEach { obj ->
                // Skaliranje koordinata unutar opsega ekrana
                val scaledX = obj.x * screenWidth
                val scaledY = obj.y * screenHeight

                println("Drawing object: ${obj.id} at (${scaledX}, ${scaledY})")

                drawCircle(
                    color = Color.Red,  // Crvena boja za bolji kontrast
                    radius = 10f,
                    center = Offset(scaledX, scaledY)
                )
            }
        }
    }
}
