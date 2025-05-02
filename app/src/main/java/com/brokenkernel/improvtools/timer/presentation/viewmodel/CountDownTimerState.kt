package com.brokenkernel.improvtools.timer.presentation.viewmodel

import com.brokenkernel.improvtools.timer.sidecar.notifications.CountDownNotificationManager
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.MutableStateFlow

internal class CountDownTimerState(
    title: MutableStateFlow<String>,
    initialTime: Duration,
    private val countDownNotificationManager: CountDownNotificationManager,
) : BaseTimerState(title, initialTime = INITIAL_TIMER_DURATION) {
    private val _myTimerThread: Timer = fixedRateTimer(
        "fixed rate timer for: $title",
        daemon = true,
        initialDelay = 0L,
        period = initialTime.inWholeMilliseconds,
    ) {
        if (_timeLeft.value.isPositive() && timerState.value.isStarted()) {
            _timeLeft.value -= 1.seconds
        }
    }
}
