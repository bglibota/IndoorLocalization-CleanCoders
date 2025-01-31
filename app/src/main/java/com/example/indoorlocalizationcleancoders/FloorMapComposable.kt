package com.example.indoorlocalizationcleancoders

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween


data class TrackedObject(
    val AssetName: String,
    val X: Float,
    val Y: Float,
    val AssetId: Int,
    val FloorMapId: Int
)

@Composable
fun FloorMapComposableWithObjects(
    modifier: Modifier = Modifier,
    trackedObjects: List<TrackedObject>
) {
    var floorMapSize by remember { mutableStateOf(IntSize(0, 0)) }
    val coroutineScope = rememberCoroutineScope()

    // Animatable instance za svaki objekt (samo za glatke prijelaze pozicija)
    val animatableOffsets = remember { mutableMapOf<String, Pair<Animatable<Float, *>, Animatable<Float, *>>>() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                floorMapSize = layoutCoordinates.size
            }
    ) {
        // Prikaz tlocrta
        Image(
            painter = painterResource(id = R.drawable.tlocrt),
            contentDescription = "Floor Map",
            contentScale = ContentScale.FillBounds,
            modifier = modifier.fillMaxSize()
        )

        // Crtanje objekata i njihovih oznaka na tlocrtnu mapu
        Canvas(modifier = modifier.fillMaxSize()) {
            val width = floorMapSize.width.toFloat()
            val height = floorMapSize.height.toFloat()

            trackedObjects.forEach { obj ->
                // Skaliranje koordinata prema dimenzijama tlocrta
                val scaledX = obj.X / 100f * width
                val scaledY = obj.Y / 100f * height

                // Dohvaćanje ili inicijalizacija Animatable za glatku animaciju
                val animatable = animatableOffsets.getOrPut(obj.AssetName) {
                    Animatable(scaledX) to Animatable(scaledY)
                }

                // Pokretanje animacije na novu poziciju
                coroutineScope.launch {
                    animatable.first.animateTo(scaledX)
                    animatable.second.animateTo(scaledY)
                }

                // Crtanje kružića za objekt
                drawCircle(
                    color = Color.Red,
                    radius = 15f,
                    center = Offset(animatable.first.value, animatable.second.value)
                )

                // Crtanje oznake (ID-a) pored objekta
                val textPaint = Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                    textSize = 15.sp.toPx()
                    color = android.graphics.Color.BLACK
                }

                drawContext.canvas.nativeCanvas.drawText(
                    obj.AssetName,
                    animatable.first.value + 15, // Pomak desno od objekta
                    animatable.second.value + 5, // Lagani pomak dolje
                    textPaint
                )
            }
        }
    }
}
