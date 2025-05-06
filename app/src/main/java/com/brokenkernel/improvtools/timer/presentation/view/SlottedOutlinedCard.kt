package com.brokenkernel.improvtools.timer.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
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
    // TODO: consider making `:` blink when started
    return String.format(Locale.current.platformLocale, "%02d:%02d:%02d", hours, minutes, seconds)
}

@Composable
internal fun SlottedTimerCardContent(
    title: String,
    currentTime: Duration,
    timerState: TimerState,
    onRemoveTimer: () -> Unit,
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable (() -> Unit) = {},
    leadingIcon: @Composable (() -> Unit) = {},
) {
    var isEditTitleMode by remember { mutableStateOf(false) }
    var currentTitleTextBox by remember { mutableStateOf(title) }

    Column(modifier = modifier) {
        Row {
            leadingIcon()
            if (isEditTitleMode) {
                OutlinedTextField(
                    value = currentTitleTextBox,
                    onValueChange = { newText: String ->
                        currentTitleTextBox = newText
                        onTitleChange(newText)
                    },
                    label = { Text(stringResource(R.string.timer_title)) },
                    textStyle = MaterialTheme.typography.headlineLarge,
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrectEnabled = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        showKeyboardOnFocus = true,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onTitleChange(title)
                            isEditTitleMode = false
                        },
                    ),
                )
                // TODO: button or something to save. Or enter.
            } else {
                Text(
                    title,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.clickable(
                        onClick = {
                            isEditTitleMode = true
                        },
                    ),
                )
            }
            Spacer(Modifier.weight(1f))
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
