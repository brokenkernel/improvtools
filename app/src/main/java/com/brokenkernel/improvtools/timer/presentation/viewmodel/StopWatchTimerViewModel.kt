package com.brokenkernel.improvtools.timer.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.brokenkernel.improvtools.timer.sidecar.notifications.StopWatchNotification
import com.brokenkernel.improvtools.timer.sidecar.notifications.StopWatchNotificationManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel(assistedFactory = StopWatchTimerViewModel.Factory::class)
internal class StopWatchTimerViewModel @AssistedInject constructor(
    @Assisted("title") title: MutableStateFlow<String>, // todo: pass TimerInfo?
    private val savedStateHandle: SavedStateHandle,
    private val stopWatchNotificationManager: StopWatchNotificationManager,
) : BaseTimerViewModel(title, initialTime = Duration.ZERO) {
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

    @AssistedFactory interface Factory {
        fun create(@Assisted("title") title: MutableStateFlow<String>): StopWatchTimerViewModel
    }
}
