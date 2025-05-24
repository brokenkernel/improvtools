package com.brokenkernel.improvtools.timer.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.components.view.DragIconButton
import com.brokenkernel.components.view.SimpleIconButton
import com.brokenkernel.improvtools.timer.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import sh.calvin.reorderable.ReorderableCollectionItemScope

private fun Long.formatTimeMoment(): String {
    return String.format(Locale.current.platformLocale, "%02d", this)
}

@OptIn(ExperimentalTime::class)
@Composable
private fun CurrentTimerTime(currentTime: () -> Duration, isStarted: Boolean) {
    val style: TextStyle = MaterialTheme.typography.displayLarge
    var showColon by remember { mutableStateOf(true) }

    val currentTimeFlow = flow<_> {
        if (isStarted) {
            delay(.5.seconds)
            showColon = !showColon
            emit(currentTime())
        }
    }

    val ctime by currentTimeFlow.collectAsStateWithLifecycle(initialValue = currentTime())

    // TODO: derivedStateOf
    // TODO: currentTime as method call seems weird and likely broken
    val hours = ctime.inWholeHours
    val minutes = (ctime - hours.hours).inWholeMinutes
    val seconds = (ctime - hours.hours - minutes.minutes).inWholeSeconds

    Row {
        Text(hours.formatTimeMoment(), style = style)
        if (showColon) {
            Text(":", style = style)
        } else {
            Text(" ", style = style)
        }
        Text(minutes.formatTimeMoment(), style = style)
        if (showColon) {
            Text(":", style = style)
        } else {
            Text(" ", style = style)
        }
        Text(seconds.formatTimeMoment(), style = style)
    }
}

// TODO: make internal
@Composable
public fun SlottedTimerCardContent(
    title: String,
    currentTime: () -> Duration,
    onRemoveTimer: () -> Unit,
    isStarted: Boolean,
    onTitleChange: (String) -> Unit,
    scope: ReorderableCollectionItemScope,
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
            SimpleIconButton(
                onClick = onRemoveTimer,
                icon = Icons.Default.Delete,
                contentDescription = stringResource(R.string.remove_timer),
            )
            DragIconButton(scope)
        }
        CurrentTimerTime(currentTime, isStarted)
        Row {
            actions()
        }
    }
}
