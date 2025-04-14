package com.brokenkernel.improvtools.timer.presentation.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds


// TODO: move timer state into view model (per timer)
// TODO: allow for optional starting time setting
// TODO control of how many timers. Add some. Remove some.
// TODO: Add titles.

const val INITIAL_TIMER_DURATION: Long = 60L // as seconds

@Composable
fun SimpleCountDownTimer(
    title: String,
    onNewTimer: (() -> Unit),
    onRemoveTimer: (() -> Unit),
) {
    var timeLeft: Duration by remember { mutableStateOf(1.minutes) }
    var timerState by remember { mutableStateOf(TimerState.PAUSED) }

    LaunchedEffect(timeLeft, timerState) {
        if (timerState == TimerState.STARTED) {
            while (timeLeft.isPositive()) {
                delay(1.seconds)
                timeLeft -= 1.seconds
            }
        }
    }

    SlottedTimerCardContent(
        title = title,
        currentTime = timeLeft,
        timerState = timerState,
        actions = {
            StartPauseButton(
                timerState,
                onPause = {
                    timerState = TimerState.PAUSED
                },
                onStart = {
                    timerState = TimerState.STARTED
                })
            OutlinedButton(onClick = {
                timeLeft /= 2
            }) {
                Text(stringResource(R.string.timer_half_time))
            }
            OutlinedButton(onClick = {
                timerState = TimerState.STOPPED
                timeLeft = INITIAL_TIMER_DURATION.seconds
            }) {
                Text(stringResource(R.string.timer_reset))
            }
        },
        onNewTimer = onNewTimer,
        onRemoveTimer = onRemoveTimer,
        leadingIcon = {
            Icon(
                Icons.Default.Timer,
                contentDescription = stringResource(R.string.count_down_timer),
            )
        }
    )
}

@Composable
fun SimpleStopWatchTimer(title: String) {
    var timeLeft: Duration by remember { mutableStateOf(Duration.ZERO) }
    var timerState by remember { mutableStateOf(TimerState.PAUSED) }

    LaunchedEffect(timeLeft, timerState) {
        if (timerState == TimerState.STARTED) {
            delay(1.seconds)
            timeLeft += 1.seconds
        }
    }

    SlottedTimerCardContent(
        title = title,
        currentTime = timeLeft,
        timerState = timerState,
        onRemoveTimer = {},
        onNewTimer = {},
        actions = {
            StartPauseButton(
                timerState = timerState,
                onStart = {
                    timerState = TimerState.STARTED
                },
                onPause = {
                    timerState = TimerState.PAUSED
                }
            )
            OutlinedButton(onClick = {
                timerState = TimerState.STOPPED
                timeLeft = Duration.ZERO
            }) {
                Text(stringResource(R.string.timer_reset))
            }
        },
    )
}

@Composable
fun TimerScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalColumnScrollbar(scrollState)
            .verticalScroll(scrollState)
    ) {
        // TODO dynamic size.
//        SwipeToDismissBox() { }
        var timerOneCDExists by remember { mutableStateOf(true) }
        var timerTwoCDExists by remember { mutableStateOf(true) }
        val timerOneSTDState = rememberSwipeToDismissBoxState()
        val timerTwoSTDState = rememberSwipeToDismissBoxState()

        when (timerOneSTDState.currentValue) {
            SwipeToDismissBoxValue.StartToEnd -> {
                // not used
            }

            SwipeToDismissBoxValue.EndToStart -> {
                timerOneCDExists = false
            }

            SwipeToDismissBoxValue.Settled -> {
                // not used
            }
        }
        when (timerTwoSTDState.currentValue) {
            SwipeToDismissBoxValue.StartToEnd -> {
                // not used
            }

            SwipeToDismissBoxValue.EndToStart -> {
                timerTwoCDExists = false
            }

            SwipeToDismissBoxValue.Settled -> {
                // not used
            }
        }

        // https://medium.com/better-programming/extending-swipetodismiss-in-jetpack-compose-7ed356df073a
        // TODO: pull dismiss out to separate content
        if (timerOneCDExists) {
            SwipeToDismissBox(
                state = timerOneSTDState,
                backgroundContent = {
                    val color by
                    animateColorAsState(
                        when (timerOneSTDState.dismissDirection) {
                            SwipeToDismissBoxValue.Settled -> Color.LightGray
                            SwipeToDismissBoxValue.StartToEnd -> Color.Red
                            SwipeToDismissBoxValue.EndToStart -> Color.Red
                        }
                    )
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color)
                    )
                },
                enableDismissFromStartToEnd = false,
                enableDismissFromEndToStart = true,
                gesturesEnabled = true,
            ) {
                TimerBorderOutlineCard {
                    SimpleCountDownTimer("Timer One", onRemoveTimer = { timerOneCDExists = false }, onNewTimer = {})
                }
            }
        }
        if (timerTwoCDExists) {
            TimerBorderOutlineCard {
                SimpleCountDownTimer("Timer Two", onRemoveTimer = { timerTwoCDExists = false }, onNewTimer = {})
            }
        }
        TimerBorderOutlineCard {
            SimpleStopWatchTimer("Stopwatch One")
        }
        TimerBorderOutlineCard {
            SimpleStopWatchTimer("Stopwatch Two")
        }
    }
}

@Composable
@Preview
fun PreviewTimerScreen() {
    TimerScreen()
}