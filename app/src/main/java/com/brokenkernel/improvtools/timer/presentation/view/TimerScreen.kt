package com.brokenkernel.improvtools.timer.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun TimerScreen() {
    Column {
        Text("Time Remaining", style = MaterialTheme.typography.displayLarge)
        ElevatedButton(onClick = {}, enabled = false) {
            Text("start")
        }
        TextButton(onClick = {}, enabled = false) {
            Text("half time")
        }
        TextButton(onClick = {}, enabled = false) {
            Text("reset to 60s")
        }
        Text("TODO: figure out how to set initial time in general")
    }
}