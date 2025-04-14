package com.brokenkernel.improvtools.timer.presentation.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.timer.presentation.viewmodel.TimerState


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