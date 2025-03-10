package com.teksoftwares.simpleutility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TimerScreen() {
    var duration by remember { mutableStateOf("") }
    var remainingTime by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isRunning) {
        if (isRunning && remainingTime > 0) {
            while (remainingTime > 0) {
                delay(1000)
                remainingTime--
            }
            isRunning = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Duration (seconds)") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Remaining: $remainingTime seconds")
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                remainingTime = duration.toIntOrNull() ?: 0
                isRunning = true
            }) {
                Text("Start")
            }
            Button(onClick = { isRunning = false }) {
                Text("Pause")
            }
            Button(onClick = {
                isRunning = false
                remainingTime = 0
            }) {
                Text("Reset")
            }
        }
    }
}