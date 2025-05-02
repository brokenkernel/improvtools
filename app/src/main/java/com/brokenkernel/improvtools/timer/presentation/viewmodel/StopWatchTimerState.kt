package com.brokenkernel.improvtools.timer.presentation.viewmodel

import android.content.Context
import com.brokenkernel.improvtools.timer.sidecar.notifications.StopWatchNotification
import com.brokenkernel.improvtools.timer.sidecar.notifications.StopWatchNotificationManager
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.MutableStateFlow

internal class StopWatchTimerState(
    title: MutableStateFlow<String>, // todo: pass TimerInfo?
    private val stopWatchNotificationManager: StopWatchNotificationManager,
) : BaseTimerState(title, initialTime = Duration.ZERO) {
    private val _myTimerThread: Timer = fixedRateTimer(
        "fixed rate timer for: $title", // TODO: use TimerID
        daemon = true,
        initialDelay = 0L,
        period = 1.seconds.inWholeMilliseconds,
    ) {
        if (timerState.value.isStarted()) {
            _timeLeft.value += 1.seconds
        }
    }

    fun sendNotification(context: Context) {
        val notification: StopWatchNotification
        notification = stopWatchNotificationManager.getStopWatchNotification(context, _timeLeft.value)
        stopWatchNotificationManager.send(notification)
    }
}
