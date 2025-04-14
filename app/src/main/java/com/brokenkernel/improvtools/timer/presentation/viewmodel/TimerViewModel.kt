package com.brokenkernel.improvtools.timer.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

private const val INITIAL_TIMER_SECONDS: Long = 60L // as seconds
val INITIAL_TIMER_DURATION = INITIAL_TIMER_SECONDS.seconds

// TODO: consider migrating to assisted inject. Will be required for using injected timer, etc.

internal enum class TimerState {
    STARTED,
    PAUSED,
    STOPPED
    ;

    fun isStarted(): Boolean {
        return this == STARTED
    }
}

internal sealed class BaseTimerViewModel(
    val title: String,
) : ViewModel() {

    private val _timerState: MutableStateFlow<TimerState> = MutableStateFlow(TimerState.STOPPED)
    internal val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    // TODO: consider three different funs instead of generic setter?
    fun setTimerState(state: TimerState) {
        _timerState.value = state
    }
}

internal class CountDownTimerViewModel(
    title: String,
) : BaseTimerViewModel(title) {

}

internal class StopWatchTimerViewModel(
    title: String,
) : BaseTimerViewModel(title) {
}


@HiltViewModel
internal class TimerListViewModel @Inject constructor(val settingsRespository: SettingsRepository) : ViewModel() {
    init {
        viewModelScope.launch {
            settingsRespository.userSettingsFlow.collectLatest { it ->
                _shouldHaptic.value =
                    it.hapticFeedbackTimerMode != UserSettings.TimerHapticsMode.TIMER_HAPTICS_MODE_NONE
            }
        }
    }

    enum class TimerType {
        STOPWATCH,
        COUNTDOWN,
    }

    internal data class TimerInfo(
        val title: String,
        val timerType: TimerType,
    )

    private val _shouldHaptic = MutableStateFlow(true)
    val shouldHaptic = _shouldHaptic.asStateFlow()

    // hide the mutable ability from the UI
    private val _allTimers: MutableStateFlow<MutableList<TimerInfo>> = MutableStateFlow<MutableList<TimerInfo>>(
        mutableStateListOf(
            TimerInfo("Stopwatch One", TimerType.STOPWATCH),
            TimerInfo("Stopwatch Two", TimerType.STOPWATCH),
            TimerInfo("Countdown Three", TimerType.COUNTDOWN),
            TimerInfo("Countdown Four", TimerType.COUNTDOWN),
        )
    )
    val allTimers = _allTimers.asStateFlow()

    fun removeTimer(timerInfo: TimerInfo) {
        _allTimers.value.remove(timerInfo)
    }

    fun addTimer(title: String, timerType: TimerType) {
        _allTimers.value.add(TimerInfo(title, timerType))
    }
}