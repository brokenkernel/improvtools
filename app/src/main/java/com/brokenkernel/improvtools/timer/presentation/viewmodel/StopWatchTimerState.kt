package com.brokenkernel.improvtools.timer.presentation.viewmodel

import android.content.Context
import com.brokenkernel.improvtools.timer.sidecar.notifications.StopWatchNotification
import com.brokenkernel.improvtools.timer.sidecar.notifications.StopWatchNotificationManager
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.MutableStateFlow

internal class StopWatchTimerState(
    title: MutableStateFlow<String>, // todo: pass TimerInfo?
    private val stopWatchNotificationManager: StopWatchNotificationManager,
) : BaseTimerState(title, initialTime = Duration.ZERO) {

    fun sendNotification(context: Context) {
        val notification: StopWatchNotification =
            stopWatchNotificationManager.getStopWatchNotification(context, timeLeft.value)
        stopWatchNotificationManager.send(notification)
    }

    override fun tick() {
        if (timerState.value.isStarted()) {
            setTimeLeft(timeLeft.value + 1.seconds)
        }
    }
}
