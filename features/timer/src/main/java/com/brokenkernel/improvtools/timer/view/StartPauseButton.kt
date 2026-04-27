package com.brokenkernel.improvtools.timer.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.timer.R
import com.brokenkernel.improvtools.timer.model.TimerState

// TODO: make internal

@Composable
public fun StartPauseButton(
    timerState: TimerState,
    onStart: (() -> Unit),
    onPause: (() -> Unit),
    modifier: Modifier = Modifier,
) {
    val isStartedState = timerState.isStarted().collectAsState()
    Button(
        onClick = {
            if (isStartedState.value) {
                onPause()
            } else {
                onStart()
            }
        },
    ) {
        val curButtonText: String =
            if (isStartedState.value) {
                stringResource(R.string.pause)
            } else {
                stringResource(R.string.start)
            }
        val curButtonIcon: ImageVector =
            if (isStartedState.value) {
                Icons.Default.Pause
            } else {
                Icons.Default.PlayArrow
            }
        Icon(
            curButtonIcon,
            contentDescription = curButtonText,
        )
        Text(curButtonText)
    }
}
