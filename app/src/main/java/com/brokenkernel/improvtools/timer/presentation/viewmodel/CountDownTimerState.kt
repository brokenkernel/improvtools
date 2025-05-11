package com.brokenkernel.improvtools.timer.presentation.viewmodel

import com.brokenkernel.improvtools.timer.sidecar.notifications.CountDownNotificationManager
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.MutableStateFlow

internal class CountDownTimerState(
    title: MutableStateFlow<String>,
    initialTime: Duration = INITIAL_TIMER_DURATION,
    private val countDownNotificationManager: CountDownNotificationManager,
) : BaseTimerState(title, initialTime = initialTime) {
    override fun tick() {
        val timeleft = timeLeft.value
        if (timeleft.isPositive() && timerState.value.isStarted()) {
            setTimeLeft(timeleft - 1.seconds)
        }
    }
}
