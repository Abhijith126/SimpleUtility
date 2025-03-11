package com.teksoftwares.simpleutility

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ClockScreen() {
    var currentTime by remember { mutableStateOf(LocalTime.now()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = LocalTime.now()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerX = size.width / 2
                val centerY = size.height / 2
                val radius = minOf(centerX, centerY) * 0.8f

                // Draw the clock face (circle)
                drawCircle(
                    color = Color.Black,
                    radius = radius,
                    center = Offset(centerX, centerY),
                    style = Stroke(width = 2.dp.toPx()) // Correct usage of Stroke
                )

                val hour = currentTime.hour % 12
                val minute = currentTime.minute
                val second = currentTime.second

                val hourAngle = (hour + minute / 60f) * 30f - 90f
                val minuteAngle = (minute + second / 60f) * 6f - 90f
                val secondAngle = second * 6f - 90f

                val hourLength = radius * 0.5f
                val minuteLength = radius * 0.7f
                val secondLength = radius * 0.9f

                drawLine(
                    color = Color.Black,
                    start = Offset(centerX, centerY),
                    end = Offset(
                        centerX + hourLength * cos(Math.toRadians(hourAngle.toDouble()).toFloat()),
                        centerY + hourLength * sin(Math.toRadians(hourAngle.toDouble()).toFloat())
                    ),
                    strokeWidth = 8.dp.toPx(),
                    cap = StrokeCap.Round
                )

                drawLine(
                    color = Color.Black,
                    start = Offset(centerX, centerY),
                    end = Offset(
                        centerX + minuteLength * cos(Math.toRadians(minuteAngle.toDouble()).toFloat()),
                        centerY + minuteLength * sin(Math.toRadians(minuteAngle.toDouble()).toFloat())
                    ),
                    strokeWidth = 4.dp.toPx(),
                    cap = StrokeCap.Round
                )

                drawLine(
                    color = Color.Red,
                    start = Offset(centerX, centerY),
                    end = Offset(
                        centerX + secondLength * cos(Math.toRadians(secondAngle.toDouble()).toFloat()),
                        centerY + secondLength * sin(Math.toRadians(secondAngle.toDouble()).toFloat())
                    ),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(bottom = 15.dp)) {
            Text(text = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), fontSize = 30.sp)
        }
    }
}