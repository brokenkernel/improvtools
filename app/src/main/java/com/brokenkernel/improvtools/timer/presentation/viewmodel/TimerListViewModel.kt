package com.brokenkernel.improvtools.timer.presentation.viewmodel

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import com.brokenkernel.improvtools.timer.data.repository.TimerManager
import com.brokenkernel.improvtools.timer.model.CountDownTimerState
import com.brokenkernel.improvtools.timer.model.CountUpTimerState
import com.brokenkernel.improvtools.timer.model.INITIAL_COUNT_DOWN_TIMER_DURATION
import com.brokenkernel.improvtools.timer.model.PausedCountDownTimerState
//import com.brokenkernel.improvtools.timer.model.PausedCountUpTimerState
import com.brokenkernel.improvtools.timer.model.PausedTimerState
import com.brokenkernel.improvtools.timer.model.StartedTimerState
import com.brokenkernel.improvtools.timer.model.TimerState
import com.brokenkernel.improvtools.timer.model.TrueCountDownTimerState
import com.brokenkernel.improvtools.timer.sidecar.notifications.CountDownNotificationManager
import com.brokenkernel.improvtools.timer.sidecar.notifications.StopWatchNotificationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalTime::class)
@HiltViewModel
internal class TimerListViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val timerManager: TimerManager,
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
    private val _allTimers: SnapshotStateList<TrueCountDownTimerState> =
        mutableStateListOf(
            TrueCountDownTimerState(
                mutableStateOf(INITIAL_COUNT_DOWN_TIMER_DURATION),
                mutableStateOf(false),
                mutableStateOf("Countdown Three"),
                timerManager.getNextID()
            ),
            TrueCountDownTimerState(
                mutableStateOf(INITIAL_COUNT_DOWN_TIMER_DURATION),
                mutableStateOf(false),
                mutableStateOf("Countdown Four"),
                timerManager.getNextID()
            ),
        )

    //            PausedCountUpTimerState(Duration.ZERO, "Stopwatch One", timerManager.getNextID()),
//            PausedCountUpTimerState(Duration.ZERO, "Stopwatch Two", timerManager.getNextID()),
//            mutableStateOf( PausedCountDownTimerState(INITIAL_COUNT_DOWN_TIMER_DURATION, "Countdown Three", timerManager.getNextID())),
//            mutableStateOf(PausedCountDownTimerState(INITIAL_COUNT_DOWN_TIMER_DURATION, "Countdown Four", timerManager.getNextID())),
//        )
    val allTimers = _allTimers

    fun removeTimer(timer: TrueCountDownTimerState) {
        _allTimers.remove(timer)
    }

    private fun startTimer(timer: TrueCountDownTimerState) {
        val indexToUpdate = _allTimers.indexOf(timer)
        _allTimers[indexToUpdate].isStarted.value = true
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

    private fun pauseTimer(timer: TrueCountDownTimerState) {
        val index = _allTimers.indexOf(timer)
        _allTimers[index].isStarted.value = false
    }

    fun invertTimerState(timer: TrueCountDownTimerState) {
        timer.isStarted.value = !timer.isStarted.value
    }

    fun resetTimer(timer: TrueCountDownTimerState) {
        val index = _allTimers.indexOf(timer)
        _allTimers[index].remainingTime.value = INITIAL_COUNT_DOWN_TIMER_DURATION
    }

    fun halfTimer(timer: TrueCountDownTimerState) {
        val index = _allTimers.indexOf(timer)
        _allTimers[index].remainingTime.value /= 2
    }

    fun addCountUpTimer(title: String) {
//        val timer = PausedCountUpTimerState(Duration.ZERO, title, timerManager.getNextID())
//        _allTimers.add(timer)
    }

    fun addCountDownTimer(title: String) {
        val timer = TrueCountDownTimerState(
            mutableStateOf(INITIAL_COUNT_DOWN_TIMER_DURATION),
            isStarted = mutableStateOf(false),
            mutableStateOf(title),
            timerManager.getNextID()

        )
        _allTimers.add(timer)
    }

    @OptIn(ExperimentalTime::class)
    fun replaceTitle(timer: TrueCountDownTimerState, newTitle: String) {
        val index = _allTimers.indexOf(timer)
        _allTimers[index].title.value = newTitle
    }

    fun swapTimer(from: Int, to: Int) {
        _allTimers.apply {
            add(to, removeAt(from))
        }
    }
}
