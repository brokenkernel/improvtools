package com.brokenkernel.improvtools.timer.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
internal class TimerListViewModel @Inject constructor(
    val settingsRepository: SettingsRepository,
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
                TimerInfo("Stopwatch One", TimerType.STOPWATCH, 1),
                TimerInfo("Stopwatch Two", TimerType.STOPWATCH, 2),
                TimerInfo("Countdown Three", TimerType.COUNTDOWN, 3),
                TimerInfo("Countdown Four", TimerType.COUNTDOWN, 4),
            ),
        )
    val allTimers = _allTimers.asStateFlow()

    fun removeTimer(timerInfo: TimerInfo) {
        _allTimers.value.remove(timerInfo)
    }

    fun addTimer(title: String, timerType: TimerType) {
        // TODO: ID should be derived from some 'timer manager' like notifications and be independent of
        // of the viewmodel
        _allTimers.value.add(TimerInfo(title, timerType, 5)) // TODO: ID FIXME
    }
}
