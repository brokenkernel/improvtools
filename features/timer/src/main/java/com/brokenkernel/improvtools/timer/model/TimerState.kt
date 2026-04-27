package com.brokenkernel.improvtools.timer.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

private const val INITIAL_TIMER_SECONDS: Long = 60L // as seconds
public val INITIAL_COUNT_DOWN_TIMER_DURATION: Duration = INITIAL_TIMER_SECONDS.seconds

// TODO: inExact/exact alarm at end
// TODO all sorts of tests.
// TODO: timezones and friends. Also tests
// TODO: make things internal/private as needed. Currently public while code is being modularised

public sealed class TimerState {
    private val _isStarted: MutableStateFlow<Boolean> = MutableStateFlow(false)

    public fun isStarted(): StateFlow<Boolean> {
        return _isStarted.asStateFlow()
    }

    public fun startTimer() {
        _isStarted.value = true
    }

    public fun pauseTimer() {
        _isStarted.value = false
    }

    public fun invertTimer() {
        if (_isStarted.value) {
            pauseTimer()
        } else {
            startTimer()
        }
    }


    /**
     * This is the time to show on the screen.
     */
    public abstract fun showTime(): Duration
    public abstract fun asResetTimer(): TimerState // really irksome there are no true traits.
    public abstract val title: String
    public abstract val timerID: Int

    public abstract fun asEdited(
        title: String = this.title,
    ): TimerState
}

public sealed class CountDownTimerState : TimerState() {
//    public fun asHalfTime(): CountDownTimerState

    public companion object
}

public sealed class CountUpTimerState : TimerState() {
    public companion object
}

@OptIn(ExperimentalTime::class)
public class StartedCountUpTimerState(
    private val priorElapsedTime: Duration,
    private val startedTime: Instant,
    override val title: String,
    override val timerID: Int,
) : CountUpTimerState() {

    private fun timeSinceStarted(): Duration {
        val now = Clock.System.now()
        return (now - startedTime)
    }

    private fun totalElapsedTime(): Duration {
        return priorElapsedTime + timeSinceStarted()
    }

    //        return PausedCountUpTimerState(totalElapsedTime(), title, timerID)

    override fun showTime(): Duration {
        return totalElapsedTime()
    }

    override fun asResetTimer(): TimerState {
        return PausedCountUpTimerState(Duration.ZERO, title, timerID)
    }

    override fun asEdited(
        title: String,
    ): TimerState {
        return StartedCountUpTimerState(
            priorElapsedTime = this.priorElapsedTime,
            startedTime = this.startedTime,
            title = title,
            timerID = timerID,
        )
    }
}

@OptIn(ExperimentalTime::class)
public class StartedCountDownTimerState(
    private val priorRemainingTime: Duration,
    private val startedTime: Instant,
    override val title: String,
    override val timerID: Int,
) : CountDownTimerState() {

    private fun timeSinceStarted(): Duration {
        val now = Clock.System.now()
        return (now - startedTime)
    }

    private fun totalRemainingTime(): Duration {
        return priorRemainingTime - timeSinceStarted()
    }

    override fun showTime(): Duration {
        return totalRemainingTime()
    }

    override fun asResetTimer(): TimerState {
        return PausedCountDownTimerState(INITIAL_COUNT_DOWN_TIMER_DURATION, title, timerID)
    }

//    override fun asHalfTime(): CountDownTimerState {
//        val now = Clock.System.now()
//        return StartedCountDownTimerState(totalRemainingTime() / 2, now, title, timerID)
//    }

    override fun asEdited(
        title: String,
    ): TimerState {
        return StartedCountDownTimerState(
            priorRemainingTime = this.priorRemainingTime,
            startedTime = this.startedTime,
            title = title,
            timerID = timerID,
        )
    }
}

// TODO: secondary constructor for initial creation ?

@OptIn(ExperimentalTime::class)
public class PausedCountUpTimerState(
    private val elapsedTime: Duration,
    override val title: String,
    override val timerID: Int,
) : CountUpTimerState() {
    //        val now = Clock.System.now()
//        return StartedCountUpTimerState(elapsedTime, now, title, timerID)

    override fun showTime(): Duration {
        return elapsedTime
    }

    override fun asResetTimer(): TimerState {
        return PausedCountUpTimerState(Duration.ZERO, title, timerID)
    }

    override fun asEdited(
        title: String,
    ): TimerState {
        return PausedCountUpTimerState(
            elapsedTime = this.elapsedTime,
            title = title,
            timerID = timerID,
        )
    }
}

@OptIn(ExperimentalTime::class)
public class PausedCountDownTimerState(
    private val remainingTime: Duration,
    override val title: String,
    override val timerID: Int,
) : CountDownTimerState() {
    //        val now = Clock.System.now()
//        return StartedCountDownTimerState(remainingTime, now, title, timerID)

    override fun showTime(): Duration {
        return remainingTime
    }

    override fun asResetTimer(): TimerState {
        return PausedCountDownTimerState(INITIAL_COUNT_DOWN_TIMER_DURATION, title, timerID)
    }

//    override fun asHalfTime(): CountDownTimerState {
//        return PausedCountDownTimerState(remainingTime / 2, title, timerID)
//    }

    override fun asEdited(
        title: String,
    ): TimerState {
        return PausedCountDownTimerState(
            remainingTime = remainingTime,
            title = title,
            timerID = timerID,
        )
    }
}
