package com.brokenkernel.improvtools.timer.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import com.brokenkernel.improvtools.timer.data.model.TimerInfo
import com.brokenkernel.improvtools.timer.data.repository.TimerManager
import com.brokenkernel.improvtools.timer.sidecar.notifications.CountDownNotificationManager
import com.brokenkernel.improvtools.timer.sidecar.notifications.StopWatchNotificationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.time.Duration

@HiltViewModel
internal class TimerListViewModel @Inject constructor(
    val settingsRepository: SettingsRepository,
    val timerManager: TimerManager,
    private val stopWatchNotificationManager: StopWatchNotificationManager,
    private val countDownNotificationManager: CountDownNotificationManager,
) : ViewModel() {
    init {
        viewModelScope.launch {
            settingsRepository.userSettingsFlow.collectLatest { it ->
                _shouldHaptic.value =
                    it.hapticFeedbackTimerMode != UserSettings.TimerHapticsMode.TIMER_HAPTICS_MODE_NONE
            }
        }
    }

    enum class TimerType {
        STOPWATCH,
        COUNTDOWN,
    }

    private val _shouldHaptic = MutableStateFlow(true)
    val shouldHaptic = _shouldHaptic.asStateFlow()

    // hide the mutable ability from the UI
    private val _allTimers: MutableStateFlow<MutableList<TimerInfo>> =
        MutableStateFlow<MutableList<TimerInfo>>(
            mutableStateListOf(
                TimerInfo(MutableStateFlow("Stopwatch One"), TimerType.STOPWATCH, timerManager.getNextID()),
                TimerInfo(MutableStateFlow("Stopwatch Two"), TimerType.STOPWATCH, timerManager.getNextID()),
                TimerInfo(MutableStateFlow("Countdown Three"), TimerType.COUNTDOWN, timerManager.getNextID()),
                TimerInfo(MutableStateFlow("Countdown Four"), TimerType.COUNTDOWN, timerManager.getNextID()),
            ),
        )
    val allTimers = _allTimers.asStateFlow()

    fun removeTimer(timerInfo: TimerInfo) {
        _allTimers.value.remove(timerInfo)
    }

    fun addTimer(initialTitle: String, timerType: TimerType) {
        _allTimers.value.add(TimerInfo(MutableStateFlow(initialTitle), timerType, timerManager.getNextID()))
    }

    fun createStopWatchTimerState(initialTitle: MutableStateFlow<String>): StopWatchTimerState {
        return StopWatchTimerState(initialTitle, stopWatchNotificationManager)
    }

    fun createCountdownNotificationManager(initialTitle: MutableStateFlow<String>, initialTime: Duration): CountDownTimerState {
        return CountDownTimerState(
            initialTitle,
            initialTime,
            countDownNotificationManager,
        )
    }

}
