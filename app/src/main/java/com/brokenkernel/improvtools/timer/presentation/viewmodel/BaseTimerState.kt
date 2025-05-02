package com.brokenkernel.improvtools.timer.presentation.viewmodel

import com.brokenkernel.improvtools.timer.data.model.TimerState
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val INITIAL_TIMER_SECONDS: Long = 60L // as seconds
val INITIAL_TIMER_DURATION: Duration = INITIAL_TIMER_SECONDS.seconds

// the timer itself needs to be pulled out and have its own lifecycle + callback support
internal sealed class BaseTimerState(
    private val _title: MutableStateFlow<String>,
    initialTime: Duration,
) {

    private val _timerState: MutableStateFlow<TimerState> = MutableStateFlow(TimerState.STOPPED)
    internal val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    val title = _title.asStateFlow()

    // TODO: handle initial time originally?
    protected val _timeLeft: MutableStateFlow<Duration> = MutableStateFlow(initialTime)
    internal val timeLeft: StateFlow<Duration> = _timeLeft.asStateFlow()

    // TODO: consider three different funs instead of generic setter?
    fun setTimerState(state: TimerState) {
        _timerState.value = state
    }

    fun setTimeLeft(duration: Duration) {
        _timeLeft.value = duration
    }

    fun setTitle(newTitle: String) {
        _title.value = newTitle
    }
}
