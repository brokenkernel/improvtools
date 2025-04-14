package com.brokenkernel.improvtools.timer.presentation.view


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.brokenkernel.improvtools.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

private fun Duration.formatTime(): String {
    val hours = this.inWholeHours
    val minutes = (this - hours.hours).inWholeMinutes
    val seconds = (this - hours.hours - minutes.minutes).inWholeSeconds
    return String.format(Locale.current.platformLocale, "%02d:%02d:%02d", hours, minutes, seconds)
}

internal enum class TimerState {
    STARTED,
    PAUSED,
    STOPPED
}

@Composable()
internal fun SlottedTimerCardContent(
    title: String,
    currentTime: Duration,
    timerState: TimerState,
    actions: @Composable (() -> Unit) = {},
    onNewTimer: () -> Unit,
    onRemoveTimer: () -> Unit,
    leadingIcon: @Composable (() -> Unit) = {},
) {
    Column {
        Row {
            leadingIcon()
            Text(title, style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.weight(1f))
            IconButton(onClick = onNewTimer) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.slotted_timer_create_new_timer),
                )
            }
            IconButton(onClick = onRemoveTimer) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(R.string.slotted_timer_remove_timer),
                )
            }
        }
        Text(currentTime.formatTime(), style = MaterialTheme.typography.displayLarge)
        Row {
            actions()
        }
    }
}

@Composable
internal fun StartPauseButton(
    timerState: TimerState,
    onStart: (() -> Unit),
    onPause: (() -> Unit),
) {
    Button(onClick = {
        if (timerState == TimerState.STARTED) {
            onPause()
        } else {
            onStart()
        }
    }) {
        val curButtonText: String =
            if (timerState == TimerState.STARTED) {
                stringResource(R.string.timer_pause)
            } else {
                stringResource(R.string.timer_start)
            }
        val curButtonIcon: ImageVector =
            if (timerState == TimerState.STARTED) {
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
}

@Composable
internal fun TimerBorderOutlineCard(content: @Composable (() -> Unit) = {}) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Black),
    ) {
        content()
    }
}