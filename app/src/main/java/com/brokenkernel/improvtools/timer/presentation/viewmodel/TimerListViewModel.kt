package com.brokenkernel.improvtools.timer.presentation.viewmodel

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import com.brokenkernel.improvtools.timer.data.repository.TimerManager
import com.brokenkernel.improvtools.timer.sidecar.notifications.CountDownNotificationManager
import com.brokenkernel.improvtools.timer.sidecar.notifications.StopWatchNotificationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalTime::class)
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

    private val _shouldHaptic = MutableStateFlow(true)
    val shouldHaptic = _shouldHaptic.asStateFlow()

    // hide the mutable ability from the UI
    private val _allTimers: SnapshotStateList<TimerState> =
        mutableStateListOf(
            PausedCountUpTimerState(Duration.ZERO, "Stopwatch One", timerManager.getNextID()),
            PausedCountUpTimerState(Duration.ZERO, "Stopwatch Two", timerManager.getNextID()),
            PausedCountDownTimerState(INITIAL_COUNT_DOWN_TIMER_DURATION, "Countdown Three", timerManager.getNextID()),
            PausedCountDownTimerState(INITIAL_COUNT_DOWN_TIMER_DURATION, "Countdown Four", timerManager.getNextID()),
        )
    val allTimers: List<TimerState> = _allTimers

    fun removeTimer(timer: TimerState) {
        _allTimers.remove(timer)
    }

    private fun startTimer(timer: PausedTimerState) {
        val index = _allTimers.indexOf(timer)
        _allTimers[index] = timer.asStartedTimer()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun tryToSendNotificationForTimer(timer: TimerState, context: Context) {
        when (timer) {
            is CountUpTimerState -> {
                val notification =
                    stopWatchNotificationManager.getStopWatchNotification(context, timer)
                stopWatchNotificationManager.send(notification)
            }

            is CountDownTimerState -> {
                val notification =
                    countDownNotificationManager.getCountDownNotification(context, timer)
                countDownNotificationManager.send(notification)
            }
        }
    }

    private fun pauseTimer(timer: StartedTimerState) {
        val index = _allTimers.indexOf(timer)
        _allTimers[index] = timer.asPausedTimer()
    }

    fun invertTimerState(timer: TimerState, context: Context) {
        when (timer) {
            is PausedTimerState -> startTimer(timer)
            is StartedTimerState -> pauseTimer(timer)
        }
    }

    fun resetTimer(timer: TimerState) {
        val index = _allTimers.indexOf(timer)
        _allTimers[index] = timer.asResetTimer()
    }

    fun halfTimer(timer: CountDownTimerState) {
        val index = _allTimers.indexOf(timer)
        _allTimers[index] = timer.asHalfTime()
    }

    fun addCountUpTimer(title: String) {
        val timer = PausedCountUpTimerState(Duration.ZERO, title, timerManager.getNextID())
        _allTimers.add(timer)
    }

    fun addCountDownTimer(title: String) {
        val timer = PausedCountDownTimerState(INITIAL_COUNT_DOWN_TIMER_DURATION, title, timerManager.getNextID())
        _allTimers.add(timer)
    }

    @OptIn(ExperimentalTime::class)
    fun replaceTitle(timer: TimerState, newTitle: String) {
        val index = _allTimers.indexOf(timer)
        val priorTimer = _allTimers[index]
        when (priorTimer) {
            is PausedCountDownTimerState ->
                _allTimers[index] = priorTimer.copy(title = newTitle)

            is StartedCountDownTimerState ->
                _allTimers[index] = priorTimer.copy(title = newTitle)

            is PausedCountUpTimerState ->
                _allTimers[index] = priorTimer.copy(title = newTitle)

            is StartedCountUpTimerState ->
                _allTimers[index] = priorTimer.copy(title = newTitle)
        }
    }

    fun swapTimer(from: Int, to: Int) {
        _allTimers.apply {
            add(to, removeAt(from))
        }
    }
}
