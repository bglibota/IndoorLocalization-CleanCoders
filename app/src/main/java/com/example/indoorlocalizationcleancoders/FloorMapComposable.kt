package com.example.indoorlocalizationcleancoders

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.animation.core.*
import com.example.indoorlocalizationcleancoders.R

@Composable
fun FloorMapComposableWithMovingObjects(
    modifier: Modifier = Modifier,
    objectPositions: List<Pair<Float, Float>>, // Lista pozicija objekata
    showObject: Boolean, // Kontrola prikaza objekta
    speed: Float = 5f // Brzina kretanja
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Prikaz tlocrta na cijelom ekranu
        Image(
            painter = painterResource(id = R.drawable.tlocrt),
            contentDescription = "Floor Map",
            contentScale = ContentScale.FillBounds, // Puni ekran
            modifier = modifier.fillMaxSize() // Koristi cijeli ekran za sliku
        )

        if (showObject) {
            objectPositions.forEach { position ->
                // Animiraj X i Y pozicije za fluidno kretanje
                val animatedX = remember { Animatable(position.first) }
                val animatedY = remember { Animatable(position.second) }

                LaunchedEffect(position) {
                    // Nasumično odaberi smjer (lijevo, desno, gore, dolje)
                    val randomDirection = (1..4).random()

                    // Postavi ciljnu poziciju
                    val targetX = when (randomDirection) {
                        1 -> animatedX.value - speed // Lijevo
                        2 -> animatedX.value + speed // Desno
                        else -> animatedX.value // Zadrži trenutnu X poziciju
                    }

                    val targetY = when (randomDirection) {
                        3 -> animatedY.value - speed // Gore
                        4 -> animatedY.value + speed // Dolje
                        else -> animatedY.value // Zadrži trenutnu Y poziciju
                    }

                    // Animiraj kretanje prema novoj poziciji
                    animatedX.animateTo(
                        targetValue = targetX,
                        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
                    )

                    animatedY.animateTo(
                        targetValue = targetY,
                        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
                    )
                }

                // Crtanje točkice na animiranoj poziciji
                Canvas(modifier = modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color.Red,
                        radius = 10f, // Veličina točke
                        center = Offset(animatedX.value, animatedY.value)
                    )
                }
            }
        }
    }
}

