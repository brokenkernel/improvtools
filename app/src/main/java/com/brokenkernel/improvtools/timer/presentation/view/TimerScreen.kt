package com.brokenkernel.improvtools.timer.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import com.brokenkernel.improvtools.R
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds


// TODO: move timer state into view model (per timer)
// TODO: allow for optional starting time setting
// TODO control of how many timers. Add some. Remove some.
// TODO: Add titles.

const val INITIAL_TIMER_DURATION = 60L // as seconds

private fun Duration.formatTime(): String {
    val hours = this.inWholeHours
    val minutes = (this - hours.hours).inWholeMinutes
    val seconds = (this - hours.hours - minutes.minutes).inWholeSeconds
    return String.format(Locale.current.platformLocale, "%02d:%02d:%02d", hours, minutes, seconds)
}

@Composable
fun SimpleTimer(title: String) {
    var timerStarted by remember { mutableStateOf(false) }
    var timeLeft: Duration by remember { mutableStateOf(INITIAL_TIMER_DURATION.seconds) }

    LaunchedEffect(timeLeft, timerStarted) {
        if (timerStarted) {
            while (timeLeft.isPositive()) {
                delay(1.seconds)
                timeLeft -= 1.seconds
            }
        }
    }


    Column {
        Text(title, style = MaterialTheme.typography.headlineLarge)
        Text(timeLeft.formatTime(), style = MaterialTheme.typography.displayLarge)
        Row {
            Button(onClick = {
                timerStarted = !timerStarted
            }) {
                val curButtonText: String =
                    if (timerStarted) {
                        stringResource(R.string.timer_pause)
                    } else {
                        stringResource(R.string.timer_start)
                    }
                val curButtonIcon: ImageVector =
                    if (timerStarted) {
                        Icons.Default.Pause
                    } else {
                        Icons.Default.PlayArrow
                    }
                Icon(
                    curButtonIcon,
                    contentDescription = curButtonText
                )
                Text(curButtonText)
            }
            OutlinedButton(onClick = {
                timeLeft /= 2
            }) {
                Text(stringResource(R.string.timer_half_time))
            }
            OutlinedButton(onClick = {
                timerStarted = false
                timeLeft = INITIAL_TIMER_DURATION.seconds
            }) {
                Text(stringResource(R.string.timer_reset))
            }
        }
    }
}

@Composable
fun TimerScreen() {
    Column {
        SimpleTimer("Timer One")
        SimpleTimer("Timer Two")
    }
}