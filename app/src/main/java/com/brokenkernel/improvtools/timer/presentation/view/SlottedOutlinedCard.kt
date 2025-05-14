package com.brokenkernel.improvtools.timer.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.components.view.SimpleIconButton
import com.brokenkernel.improvtools.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import sh.calvin.reorderable.ReorderableCollectionItemScope

private fun Duration.formatTime(): String {
    val hours = this.inWholeHours
    val minutes = (this - hours.hours).inWholeMinutes
    val seconds = (this - hours.hours - minutes.minutes).inWholeSeconds
    // TODO: consider making `:` blink when started
    return String.format(Locale.current.platformLocale, "%02d:%02d:%02d", hours, minutes, seconds)
}

@OptIn(ExperimentalTime::class)
@Composable
private fun CurrentTimerTime(currentTime: () -> Duration, isStarted: Boolean) {
    val currentTimeFlow = flow<_> {
        if (isStarted) {
            delay(1.seconds)
            emit(currentTime())
        }
    }
    val currentTime by currentTimeFlow.collectAsStateWithLifecycle(initialValue = currentTime())
    Text(currentTime.formatTime(), style = MaterialTheme.typography.displayLarge)
}

@Composable
internal fun ReorderableCollectionItemScope.SlottedTimerCardContent(
    title: String,
    currentTime: () -> Duration,
    onRemoveTimer: () -> Unit,
    isStarted: Boolean,
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable (() -> Unit) = {},
    leadingIcon: @Composable (() -> Unit) = {},
) {
    var isEditTitleMode by remember { mutableStateOf(false) }
    var currentTitleTextBox by remember { mutableStateOf(title) }

    Column(modifier = modifier) {
        Row {
            val haptic = LocalHapticFeedback.current
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
            SimpleIconButton(
                onClick = onRemoveTimer,
                icon = Icons.Default.Delete,
                contentDescription = stringResource(R.string.slotted_timer_remove_timer),
            )
            SimpleIconButton(
                modifier = Modifier.longPressDraggableHandle(
                    onDragStarted = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    },
                    onDragStopped = {
                        haptic.performHapticFeedback(HapticFeedbackType.GestureEnd)
                    },
                ),
                onClick = {},
                icon = Icons.Rounded.DragHandle,
                contentDescription = stringResource(R.string.reorder),
            )
        }
        CurrentTimerTime(currentTime, isStarted)
        Row {
            actions()
        }
    }
}
