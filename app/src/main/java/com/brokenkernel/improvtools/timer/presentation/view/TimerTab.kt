package com.brokenkernel.improvtools.timer.presentation.view

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TimerOff
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.components.presentation.view.OneWayDismissableContent
import com.brokenkernel.improvtools.timer.data.model.TimerInfo
import com.brokenkernel.improvtools.timer.data.model.TimerState
import com.brokenkernel.improvtools.timer.presentation.viewmodel.CountDownTimerState
import com.brokenkernel.improvtools.timer.presentation.viewmodel.INITIAL_TIMER_DURATION
import com.brokenkernel.improvtools.timer.presentation.viewmodel.StopWatchTimerState
import com.brokenkernel.improvtools.timer.presentation.viewmodel.TimerListViewModel
import kotlin.collections.toList
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

private const val TAG = "TimerScreen"

// TODO: allow for optional starting time setting
// TODO control of how many timers. Add some.
// todo: click on title, change name of timer
// TODO central timer manager
// TODO: title does not properly recompose. Also too dilluted of where state is stored. Need to fix the entire flow of timers
// TODO: adding timer stops/resets existing timers. See also: state storage is broken.

@Composable
internal fun SimpleCountDownTimer(
    viewModel: CountDownTimerState,
    onRemoveTimer: () -> Unit,
) {
    val timeLeft by viewModel.timeLeft.collectAsStateWithLifecycle()
    val timerState by viewModel.timerState.collectAsStateWithLifecycle()
    val currentTitle = viewModel.title.collectAsStateWithLifecycle()

    SlottedTimerCardContent(
        title = currentTitle.value,
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
                },
            )
            OutlinedButton(
                onClick = {
                    viewModel.setTimeLeft(timeLeft / 2)
                },
            ) {
                Text(stringResource(R.string.timer_half_time))
            }
            OutlinedButton(
                onClick = {
                    viewModel.setTimerState(TimerState.STOPPED)
                    viewModel.setTimeLeft(INITIAL_TIMER_DURATION)
                },
            ) {
                Text(stringResource(R.string.timer_reset))
            }
        },
        onRemoveTimer = onRemoveTimer,
        leadingIcon = {
            Icon(
                if (timerState.isStarted()) {
                    Icons.Default.Timer
                } else {
                    Icons.Default.TimerOff
                },
                contentDescription = stringResource(R.string.count_down_timer),
            )
        },
        onTitleChange = {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "SimpleCountDownTimer: Timer title changed to $it")
            }
            viewModel.setTitle(it)
        },
    )
}

@Composable
internal fun SimpleStopWatchTimer(viewModel: StopWatchTimerState, onRemoveTimer: () -> Unit) {
    val timeLeft by viewModel.timeLeft.collectAsStateWithLifecycle()
    val timerState by viewModel.timerState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val currentTitle = viewModel.title.collectAsStateWithLifecycle()

    SlottedTimerCardContent(
        title = currentTitle.value,
        currentTime = timeLeft,
        timerState = timerState,
        onRemoveTimer = onRemoveTimer,
        actions = {
            StartPauseButton(
                timerState = timerState,
                onStart = {
                    viewModel.sendNotification(context)
                    viewModel.setTimerState(TimerState.STARTED)
                },
                onPause = {
                    viewModel.setTimerState(TimerState.PAUSED)
                },
            )
            OutlinedButton(
                onClick = {
                    viewModel.setTimerState(TimerState.STOPPED)
                    viewModel.setTimeLeft(Duration.ZERO)
                },
            ) {
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
        },
        onTitleChange = {
            viewModel.setTitle(it)
        },
    )
}

@Composable
internal fun TimerTab(viewModel: TimerListViewModel = hiltViewModel(), onLaunchTitleCallback: () -> Unit) {
    // TODO: consider making a BaseScreenComposable or some such
    LaunchedEffect(Unit) {
        onLaunchTitleCallback()
    }

    val haptic = LocalHapticFeedback.current
    val shouldHapticOnRemove = viewModel.shouldHaptic.collectAsStateWithLifecycle()
    val allTimers: State<MutableList<TimerInfo>> = viewModel.allTimers.collectAsStateWithLifecycle()

    LazyColumn {
        // toList to copy to avoid ConcurrentModificationException. Maybe a better way exists to handle?
        items(allTimers.value.toList(), key = { t -> t.id }) { timer: TimerInfo ->

            val currentTimer by rememberUpdatedState(timer)
            // TODO/bug: why does removing one remove all the remaining ones below?
            val onRemove = {
                if (shouldHapticOnRemove.value) {
                    haptic.performHapticFeedback(HapticFeedbackType.ToggleOff)
                }
                viewModel.removeTimer(currentTimer)
                Log.w(TAG, "removing timer $timer")
                Unit
            }
            OneWayDismissableContent(onRemove = onRemove) {
                when (timer.timerType) {
                    TimerListViewModel.TimerType.STOPWATCH -> {
                        TimerBorderOutlineCard {
                            val simpleStopWatchState = viewModel.createStopWatchTimerState(
                                timer.title,
                            )
                            SimpleStopWatchTimer(simpleStopWatchState, onRemove)
                        }
                    }

                    TimerListViewModel.TimerType.COUNTDOWN -> {
                        TimerBorderOutlineCard {
                            val simpleCountDownTimerState = viewModel.createCountdownNotificationManager(
                                timer.title,
                                initialTime = 1.minutes,
                            )
                            SimpleCountDownTimer(simpleCountDownTimerState, onRemove)
                        }
                    }
                }
            }
        }
        item(key = "bottom") {
            Row {
                val newStopwatchString = stringResource(R.string.new_stopwatch_timer)
                val newCountdownString = stringResource(R.string.new_countdown_timer)
                LargeFloatingActionButton(
                    onClick = {
                        viewModel.addTimer(newStopwatchString, TimerListViewModel.TimerType.STOPWATCH)
                    },
                ) {
                    Icon(Icons.Filled.Timer, newStopwatchString)
                }
                LargeFloatingActionButton(
                    onClick = {
                        viewModel.addTimer(newCountdownString, TimerListViewModel.TimerType.COUNTDOWN)
                    },
                ) {
                    Icon(Icons.Filled.AlarmAdd, newCountdownString)
                }
            }
        }
    }
}
