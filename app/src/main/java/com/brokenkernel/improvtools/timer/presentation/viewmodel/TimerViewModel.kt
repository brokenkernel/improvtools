package com.brokenkernel.improvtools.timer.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val INITIAL_TIMER_SECONDS: Long = 60L // as seconds
val INITIAL_TIMER_DURATION: Duration = INITIAL_TIMER_SECONDS.seconds

// TODO: consider migrating to assisted inject. Will be required for using injected timer, etc.

internal enum class TimerState {
    STARTED,
    PAUSED,
    STOPPED,
    ;

    fun isStarted(): Boolean {
        return this == STARTED
    }
}

internal sealed class BaseTimerViewModel(
    open val title: String, // TODO: when everything is assistted inject this won't be required
    initialTime: Duration,
) : ViewModel() {

    private val _timerState: MutableStateFlow<TimerState> = MutableStateFlow(TimerState.STOPPED)
    internal val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    // TODO: handle initial time originally?
    protected val _timeLeft: MutableStateFlow<Duration> = MutableStateFlow(initialTime)
    internal val timeLeft: StateFlow<Duration> = _timeLeft.asStateFlow()

    // TODO: consider three different funs instead of generic setter?
    fun setTimerState(state: TimerState) {
        _timerState.value = state
    }

    fun setTimeLeft(duration: Duration) {
        _timeLeft.value = duration
    }
}

@HiltViewModel(assistedFactory = CountDownTimerViewModel.Factory::class)
internal class CountDownTimerViewModel @AssistedInject constructor(
    @Assisted("title") override val title: String,
    private val savedStateHandle: SavedStateHandle,
) : BaseTimerViewModel(title, initialTime = INITIAL_TIMER_DURATION) {
    private val myTimerThread: Timer = fixedRateTimer(
        "fixed rate timer for: $title",
        daemon = true,
        initialDelay = 0L,
        period = 1.seconds.inWholeMilliseconds,
    ) {
        if (_timeLeft.value.isPositive() && timerState.value.isStarted()) {
            _timeLeft.value -= 1.seconds
        }
    }

    @AssistedFactory interface Factory {
        fun create(@Assisted("title") title: String): CountDownTimerViewModel
    }
}

internal class StopWatchTimerViewModel(
    title: String,
) : BaseTimerViewModel(title, initialTime = Duration.ZERO) {
    private val myTimerThread: Timer = fixedRateTimer(
        "fixed rate timer for: $title",
        daemon = true,
        initialDelay = 0L,
        period = 1.seconds.inWholeMilliseconds,
    ) {
        if (timerState.value.isStarted()) {
            _timeLeft.value += 1.seconds
        }
    }
}

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
    )

    private val _shouldHaptic = MutableStateFlow(true)
    val shouldHaptic = _shouldHaptic.asStateFlow()

    // hide the mutable ability from the UI
    private val _allTimers: MutableStateFlow<MutableList<TimerInfo>> =
        MutableStateFlow<MutableList<TimerInfo>>(
            mutableStateListOf(
                TimerInfo("Stopwatch One", TimerType.STOPWATCH),
                TimerInfo("Stopwatch Two", TimerType.STOPWATCH),
                TimerInfo("Countdown Three", TimerType.COUNTDOWN),
                TimerInfo("Countdown Four", TimerType.COUNTDOWN),
            ),
        )
    val allTimers = _allTimers.asStateFlow()

    fun removeTimer(timerInfo: TimerInfo) {
        _allTimers.value.remove(timerInfo)
    }

    fun addTimer(title: String, timerType: TimerType) {
        _allTimers.value.add(TimerInfo(title, timerType))
    }
}
