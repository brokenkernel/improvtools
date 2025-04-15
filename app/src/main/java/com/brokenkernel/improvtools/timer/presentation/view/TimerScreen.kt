package com.brokenkernel.improvtools.timer.presentation.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TimerOff
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.improvtools.APPLICATION_TAG
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.application.presentation.view.verticalColumnScrollbar
import com.brokenkernel.improvtools.timer.presentation.viewmodel.CountDownTimerViewModel
import com.brokenkernel.improvtools.timer.presentation.viewmodel.INITIAL_TIMER_DURATION
import com.brokenkernel.improvtools.timer.presentation.viewmodel.StopWatchTimerViewModel
import com.brokenkernel.improvtools.timer.presentation.viewmodel.TimerListViewModel
import com.brokenkernel.improvtools.timer.presentation.viewmodel.TimerState
import kotlin.time.Duration


// TODO: allow for optional starting time setting
// TODO control of how many timers. Add some.

@Composable
internal fun SimpleCountDownTimer(viewModel: CountDownTimerViewModel, onRemoveTimer: () -> Unit) {
    val timeLeft by viewModel.timeLeft.collectAsStateWithLifecycle()
    val timerState by viewModel.timerState.collectAsStateWithLifecycle()

    SlottedTimerCardContent(
        title = viewModel.title,
        currentTime = timeLeft,
        timerState = timerState,
        actions = {
            StartPauseButton(
                timerState,
                onPause = {
                    viewModel.setTimerState(TimerState.PAUSED)
                },
                onStart = {
                    viewModel.setTimerState(TimerState.STARTED)
                })
            OutlinedButton(onClick = {
                viewModel.setTimeLeft(timeLeft / 2)
            }) {
                Text(stringResource(R.string.timer_half_time))
            }
            OutlinedButton(onClick = {
                viewModel.setTimerState(TimerState.STOPPED)
                viewModel.setTimeLeft(INITIAL_TIMER_DURATION)
            }) {
                Text(stringResource(R.string.timer_reset))
            }
        },
        onNewTimer = { },
        onRemoveTimer = onRemoveTimer,
        leadingIcon = {
            Icon(
                if (timerState.isStarted()) {
                    Icons.Default.Timer
                } else {
                    Icons.Default.TimerOff
                }
                ,
                contentDescription = stringResource(R.string.count_down_timer),
            )
        }
    )
}

@Composable
internal fun SimpleStopWatchTimer(viewModel: StopWatchTimerViewModel, onRemoveTimer: () -> Unit) {
    val timeLeft by viewModel.timeLeft.collectAsStateWithLifecycle()
    val timerState by viewModel.timerState.collectAsStateWithLifecycle()

    SlottedTimerCardContent(
        title = viewModel.title,
        currentTime = timeLeft,
        timerState = timerState,
        onRemoveTimer = onRemoveTimer,
        onNewTimer = {},
        actions = {
            StartPauseButton(
                timerState = timerState,
                onStart = {
                    viewModel.setTimerState(TimerState.STARTED)
                },
                onPause = {
                    viewModel.setTimerState(TimerState.PAUSED)
                }
            )
            OutlinedButton(onClick = {
                viewModel.setTimerState(TimerState.STOPPED)
                viewModel.setTimeLeft(Duration.ZERO)
            }) {
                Text(stringResource(R.string.timer_reset))
            }
        },
        leadingIcon = {
            Icon(
                if (timerState.isStarted()) {
                    Icons.Default.AlarmOn
                } else {
                    Icons.Default.AlarmOff
                },
                contentDescription = stringResource(R.string.count_down_timer),
            )
        }
    )
}


@Composable
internal fun TimerScreen(viewModel: TimerListViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val haptic = LocalHapticFeedback.current
    val shouldHapticOnRemove = viewModel.shouldHaptic.collectAsStateWithLifecycle()
    val allTimers: State<MutableList<TimerListViewModel.TimerInfo>> = viewModel.allTimers.collectAsStateWithLifecycle()

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
        Row {
            LargeFloatingActionButton(
                onClick = {
                    viewModel.addTimer("wat", TimerListViewModel.TimerType.STOPWATCH)
                },
            ) {
                Icon(Icons.Filled.Timer, "New StopWatch Timer")
            }
            LargeFloatingActionButton(
                onClick = {
                    viewModel.addTimer("pot", TimerListViewModel.TimerType.COUNTDOWN)
                },
            ) {
                Icon(Icons.Filled.AlarmAdd, "New CountDown Timer")
            }
        }
    }
}