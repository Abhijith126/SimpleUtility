package com.teksoftwares.simpleutility

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun TimerScreen() {
    var duration by remember { mutableStateOf("") }
    var remainingTime by remember { mutableIntStateOf(0) }
    var totalTime by remember { mutableIntStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var hasStarted by remember { mutableStateOf(false) }

    // Timer countdown logic
    LaunchedEffect(isRunning) {
        if (isRunning && remainingTime > 0) {
            while (remainingTime > 0 && isRunning) {
                delay(1000)
                remainingTime--
            }
            isRunning = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!hasStarted) {
            Text(
                text = duration.ifEmpty { "0" },
                style = TextStyle(fontSize = 48.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            NumberPad(
                onNumberClick = { number ->
                    if (duration.length < 6) {
                        duration += number
                    }
                },
                onBackspace = {
                    if (duration.isNotEmpty()) {
                        duration = duration.dropLast(1)
                    }
                },
                onClear = {
                    duration = ""
                }
            )
        }

        if (hasStarted) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(250.dp)
            ) {
                CircularProgressIndicator(
                    progress = { if (totalTime > 0) 1 - (remainingTime.toFloat() / totalTime) else 0f },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 12.dp,
                    trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                )
                Text(
                    text = formatTimer(remainingTime),
                    style = TextStyle(fontSize = 32.sp)
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                val inputSeconds = duration.toIntOrNull() ?: 0
                if (inputSeconds > 0) {
                    remainingTime = inputSeconds
                    totalTime = inputSeconds
                    isRunning = true
                    hasStarted = true
                }
            }) {
                Text("Start")
            }
            Button(onClick = { isRunning = false }) {
                Text("Pause")
            }
            Button(onClick = {
                isRunning = false
                remainingTime = 0
                duration = ""
                hasStarted = false
            }) {
                Text("Reset")
            }
        }
    }
}

@Composable
fun NumberPad(
    onNumberClick: (String) -> Unit,
    onBackspace: () -> Unit,
    onClear: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NumberButton("1", onClick = onNumberClick)
            NumberButton("2", onClick = onNumberClick)
            NumberButton("3", onClick = onNumberClick)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NumberButton("4", onClick = onNumberClick)
            NumberButton("5", onClick = onNumberClick)
            NumberButton("6", onClick = onNumberClick)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NumberButton("7", onClick = onNumberClick)
            NumberButton("8", onClick = onNumberClick)
            NumberButton("9", onClick = onNumberClick)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NumberButton("C", onClick = { onClear() })
            NumberButton("0", onClick = onNumberClick)
            NumberButton("⌫", onClick = { onBackspace() })
        }
    }
}

@Composable
fun NumberButton(
    label: String,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
            .clickable { onClick(label) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp
        )
    }
}

fun formatTimer(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, secs)
}
