package com.brokenkernel.improvtools.timer.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import com.brokenkernel.improvtools.timer.data.repository.TimerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TimerListViewModel @Inject constructor(
    val settingsRepository: SettingsRepository,
    val timerManager: TimerManager,
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

    internal class TimerInfo(
        val title: String,
        val timerType: TimerType,
        val id: Int,
    )

    private val _shouldHaptic = MutableStateFlow(true)
    val shouldHaptic = _shouldHaptic.asStateFlow()

    // hide the mutable ability from the UI
    private val _allTimers: MutableStateFlow<MutableList<TimerInfo>> =
        MutableStateFlow<MutableList<TimerInfo>>(
            mutableStateListOf(
                TimerInfo("Stopwatch One", TimerType.STOPWATCH, timerManager.getNextID()),
                TimerInfo("Stopwatch Two", TimerType.STOPWATCH, timerManager.getNextID()),
                TimerInfo("Countdown Three", TimerType.COUNTDOWN, timerManager.getNextID()),
                TimerInfo("Countdown Four", TimerType.COUNTDOWN, timerManager.getNextID()),
            ),
        )
    val allTimers = _allTimers.asStateFlow()

    fun removeTimer(timerInfo: TimerInfo) {
        _allTimers.value.remove(timerInfo)
    }

    fun addTimer(title: String, timerType: TimerType) {
        // TODO: ID should be derived from some 'timer manager' like notifications and be independent of
        // of the viewmodel
        _allTimers.value.add(TimerInfo(title, timerType, timerManager.getNextID())) // TODO: ID FIXME
    }
}
