package com.brokenkernel.improvtools.timer.view

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
    Button(
        onClick = {
            if (timerState.isStarted()) {
                onPause()
            } else {
                onStart()
            }
        },
        modifier = modifier,
    ) {
        val curButtonText: String =
            if (timerState.isStarted()) {
                stringResource(R.string.pause)
            } else {
                stringResource(R.string.start)
            }
        val curButtonIcon: ImageVector =
            if (timerState.isStarted()) {
                ImageVector.vectorResource(R.drawable.pause_24dp_1f1f1f_fill0_wght400_grad0_opsz24)
            } else {
                ImageVector.vectorResource(R.drawable.play_arrow_24dp_1f1f1f_fill0_wght400_grad0_opsz24)
            }
        Icon(
            curButtonIcon,
            contentDescription = curButtonText,
        )
        Text(curButtonText)
    }
}
