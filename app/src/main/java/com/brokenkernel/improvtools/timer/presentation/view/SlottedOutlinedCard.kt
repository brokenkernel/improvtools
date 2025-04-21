package com.brokenkernel.improvtools.timer.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.timer.data.model.TimerState
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

private fun Duration.formatTime(): String {
    val hours = this.inWholeHours
    val minutes = (this - hours.hours).inWholeMinutes
    val seconds = (this - hours.hours - minutes.minutes).inWholeSeconds
    return String.format(Locale.current.platformLocale, "%02d:%02d:%02d", hours, minutes, seconds)
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
