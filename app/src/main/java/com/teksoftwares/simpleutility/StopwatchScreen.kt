package com.teksoftwares.simpleutility

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun StopwatchScreen() {
    var elapsedTime by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    var lapTimes by remember { mutableStateOf(mutableListOf<Long>()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isRunning) {
        val startTime = System.currentTimeMillis()
        while (isRunning) {
            delay(10)
            elapsedTime = System.currentTimeMillis() - startTime
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = formatTime(elapsedTime))
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { isRunning = true }) {
                Text("Start")
            }
            Button(onClick = { isRunning = false }) {
                Text("Pause")
            }
            Button(onClick = {
                isRunning = false
                elapsedTime = 0L
                lapTimes.clear()
            }) {
                Text("Reset")
            }
            Button(onClick = { lapTimes.add(elapsedTime) }) {
                Text("Lap")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(lapTimes) { time ->
                Text(text = formatTime(time))
            }
        }
    }
}

fun formatTime(millis: Long): String {
    val seconds = (millis / 1000) % 60
    val minutes = (millis / (1000 * 60)) % 60
    val milliseconds = millis % 1000
    return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds)
}