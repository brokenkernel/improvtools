package com.brokenkernel.improvtools.timer.presentation.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.brokenkernel.improvtools.APPLICATION_TAG
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.timer.presentation.viewmodel.CountDownTimerViewModel
import com.brokenkernel.improvtools.timer.presentation.viewmodel.INITIAL_TIMER_DURATION
import com.brokenkernel.improvtools.timer.presentation.viewmodel.StopWatchTimerViewModel
import com.brokenkernel.improvtools.timer.presentation.viewmodel.TimerListViewModel
import com.brokenkernel.improvtools.timer.presentation.viewmodel.TimerState
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


// TODO: allow for optional starting time setting
// TODO control of how many timers. Add some. Remove some.

@Composable
internal fun SimpleCountDownTimer(viewModel: CountDownTimerViewModel, onRemoveTimer: () -> Unit) {
    // TODO: move into viewModel (and updates off the UI thread :))
    var timeLeft: Duration by remember { mutableStateOf(INITIAL_TIMER_DURATION) }
    val timerState = viewModel.timerState.collectAsState()

    LaunchedEffect(timeLeft, timerState.value) {
        if (timerState.value.isStarted()) {
            while (timeLeft.isPositive()) {
                delay(1.seconds)
                timeLeft -= 1.seconds
            }
        }
    }

    SlottedTimerCardContent(
        title = viewModel.title,
        currentTime = timeLeft,
        timerState = timerState.value,
        actions = {
            StartPauseButton(
                timerState.value,
                onPause = {
                    viewModel.setTimerState(TimerState.PAUSED)
                },
                onStart = {
                    viewModel.setTimerState(TimerState.STARTED)
                })
            OutlinedButton(onClick = {
                timeLeft /= 2
            }) {
                Text(stringResource(R.string.timer_half_time))
            }
            OutlinedButton(onClick = {
                viewModel.setTimerState(TimerState.STOPPED)
                timeLeft = INITIAL_TIMER_DURATION
            }) {
                Text(stringResource(R.string.timer_reset))
            }
        },
        onNewTimer = { },
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
internal fun SimpleStopWatchTimer(viewModel: StopWatchTimerViewModel, onRemoveTimer: () -> Unit) {
    var timeLeft: Duration by remember { mutableStateOf(Duration.ZERO) }
    val timerState = viewModel.timerState.collectAsState()

    LaunchedEffect(timeLeft, timerState.value) {
        if (timerState.value.isStarted()) {
            delay(1.seconds)
            timeLeft += 1.seconds
        }
    }

    SlottedTimerCardContent(
        title = viewModel.title,
        currentTime = timeLeft,
        timerState = timerState.value,
        onRemoveTimer = onRemoveTimer,
        onNewTimer = {},
        actions = {
            StartPauseButton(
                timerState = timerState.value,
                onStart = {
                    viewModel.setTimerState(TimerState.STARTED)
                },
                onPause = {
                    viewModel.setTimerState(TimerState.PAUSED)
                }
            )
            OutlinedButton(onClick = {
                viewModel.setTimerState(TimerState.STOPPED)
                timeLeft = Duration.ZERO
            }) {
                Text(stringResource(R.string.timer_reset))
            }
        },
        leadingIcon = {
            Icon(
                Icons.Outlined.Timer, // timerOff
                contentDescription = stringResource(R.string.count_down_timer),
            )
        }
    )
}


@Composable
internal fun TimerScreen(viewModel: TimerListViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val haptic = LocalHapticFeedback.current
    val shouldHapticOnRemove = viewModel.shouldHaptic.collectAsState()
    val allTimers: State<MutableList<TimerListViewModel.TimerInfo>> = viewModel.allTimers.collectAsState()

    Column(
        modifier = Modifier
            .verticalColumnScrollbar(scrollState)
            .verticalScroll(scrollState)
    ) {


        // toList to copy to avoid ConcurrentModificationException. Maybe a better way exists to handle?
        allTimers.value.toList().forEach { timer ->

            // TODO/bug: why does removing one remove all the remaining ones below?
            val onRemove = {
                if (shouldHapticOnRemove.value) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
                viewModel.removeTimer(timer)
                Log.w(APPLICATION_TAG, "removing timer $timer")
                Unit
            }
            OneWayDismissableContent(onRemove = onRemove) {
                when (timer.timerType) {
                    TimerListViewModel.TimerType.STOPWATCH -> {
                        TimerBorderOutlineCard {
                            SimpleStopWatchTimer(StopWatchTimerViewModel(timer.title), onRemove)
                        }

                    }

                    TimerListViewModel.TimerType.COUNTDOWN -> {
                        TimerBorderOutlineCard {
                            SimpleCountDownTimer(CountDownTimerViewModel(timer.title), onRemove)
                        }
                    }
                }
            }
        }
    }
}