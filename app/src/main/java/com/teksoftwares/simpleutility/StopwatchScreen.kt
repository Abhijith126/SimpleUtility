package com.teksoftwares.simpleutility

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.Locale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.ui.draw.scale

@Composable
fun StopwatchScreen() {
    var elapsedTime by remember { mutableLongStateOf(0L) }
    var lastStartTime by remember { mutableLongStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    val lapTimes = remember { mutableStateListOf<Long>() }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(10)
            elapsedTime = (System.currentTimeMillis() - lastStartTime) + lapTimes.sum()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = formatTime(elapsedTime),
            style = TextStyle(fontSize = 56.sp),
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = {
                if (!isRunning) {
                    lastStartTime = System.currentTimeMillis() - elapsedTime
                    isRunning = true
                }
            }) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Start", modifier = Modifier.scale(1.5f))
            }
            IconButton(onClick = { isRunning = false }) {
                Icon(imageVector = Icons.Default.Pause, contentDescription = "Pause", modifier = Modifier.scale(1.5f))
            }
            IconButton(onClick = {
                isRunning = false
                elapsedTime = 0L
                lastStartTime = 0L
                lapTimes.clear()
            }) {
                Icon(imageVector = Icons.Default.RestartAlt, contentDescription = "Reset", modifier = Modifier.scale(1.5f))
            }
            IconButton(onClick = {
                if (isRunning) lapTimes.add(elapsedTime)
            }) {
                Icon(imageVector = Icons.Default.Flag, contentDescription = "Lap", modifier = Modifier.scale(1.5f))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(lapTimes) { index, time ->
                Text(
                    text = "Lap ${index + 1}: ${formatTime(time)}",
                    style = TextStyle(fontSize = 24.sp)
                )
            }
        }
    }
}

fun formatTime(millis: Long): String {
    val minutes = (millis / (1000 * 60)) % 60
    val seconds = (millis / 1000) % 60
    val milliseconds = millis % 1000
    return String.format(Locale.getDefault(), "%02d:%02d:%03d", minutes, seconds, milliseconds)
}
