package com.brokenkernel.improvtools.timer.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.brokenkernel.improvtools.timer.sidecar.notifications.CountDownNotificationManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration.Companion.seconds

@HiltViewModel(assistedFactory = CountDownTimerViewModel.Factory::class)
internal class CountDownTimerViewModel @AssistedInject constructor(
    @Assisted("title") title: String,
    @Assisted("initialTime") initialTimeInWholeMilliseconds: Long,
    private val savedStateHandle: SavedStateHandle,
    private val countDownNotificationManager: CountDownNotificationManager,
) : BaseTimerViewModel(title, initialTime = INITIAL_TIMER_DURATION) {
    private val _myTimerThread: Timer = fixedRateTimer(
        "fixed rate timer for: $title",
        daemon = true,
        initialDelay = 0L,
        period = initialTimeInWholeMilliseconds,
    ) {
        if (_timeLeft.value.isPositive() && timerState.value.isStarted()) {
            _timeLeft.value -= 1.seconds
        }
    }

    @AssistedFactory interface Factory {
        fun create(
            @Assisted("title") title: String,
            @Assisted("initialTime") initialTimeInWholeMilliseconds: Long,
        ): CountDownTimerViewModel
    }
}
