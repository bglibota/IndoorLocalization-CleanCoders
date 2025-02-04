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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.launch
import androidx.compose.animation.core.Animatable
import com.example.indoorlocalizationcleancoders.data.models.TrackedObject


@Composable
fun FloorMapComposableWithObjects(
    modifier: Modifier = Modifier,
    trackedObjects: List<TrackedObject>,
    activeFloormap: String
) {
    var floorMapSize by remember { mutableStateOf(IntSize(0, 0)) }
    val coroutineScope = rememberCoroutineScope()
    val animatableOffsets = remember { mutableStateMapOf<String, Pair<Animatable<Float, *>, Animatable<Float, *>>>() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                floorMapSize = layoutCoordinates.size
            }
    ) {
        val floorMapResId = when (activeFloormap) {
            "assets/Tlocrt.png" -> R.drawable.tlocrt
            "assets/Tlocrt2.jpg" -> R.drawable.tlocrt2
            "assets/Tlocrt3.png" -> R.drawable.tlocrt3
            else -> R.drawable.tlocrt
        }

        Image(
            painter = painterResource(id = floorMapResId),
            contentDescription = "Floor Map",
            contentScale = ContentScale.FillBounds,
            modifier = modifier.fillMaxSize()
        )

        Canvas(modifier = modifier.fillMaxSize()) {
            val width = floorMapSize.width.toFloat()
            val height = floorMapSize.height.toFloat()

            trackedObjects.forEach { obj ->
                val scaledX = obj.X / 100f * width
                val scaledY = obj.Y / 100f * height

                val animatable = animatableOffsets.getOrPut(obj.AssetName) {
                    Animatable(scaledX) to Animatable(scaledY)
                }

                coroutineScope.launch {
                    animatable.first.animateTo(scaledX)
                    animatable.second.animateTo(scaledY)
                }

                drawCircle(
                    Color.Red,
                    radius = 15f,
                    center = Offset(animatable.first.value, animatable.second.value)
                )

                drawContext.canvas.nativeCanvas.drawText(
                    obj.AssetName,
                    animatable.first.value + 15,
                    animatable.second.value + 5,
                    Paint().asFrameworkPaint().apply {
                        textSize = 40f
                        color = android.graphics.Color.BLACK
                    }
                )
            }
        }
    }
}
