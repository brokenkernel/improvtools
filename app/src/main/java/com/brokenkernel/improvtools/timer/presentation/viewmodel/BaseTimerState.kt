package com.brokenkernel.improvtools.timer.presentation.viewmodel

import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

private const val INITIAL_TIMER_SECONDS: Long = 60L // as seconds
val INITIAL_COUNT_DOWN_TIMER_DURATION: Duration = INITIAL_TIMER_SECONDS.seconds

// TODO: inExact/exact alarm at end
// TODO all sorts of tests.
// TODO: timezones and friends. Also tests

internal sealed interface TimerState {
    /**
     * This is the time to show on on the screen.
     */
    fun showTime(): Duration
    fun asResetTimer(): TimerState // really irksome there are no true traits.
    val title: String
    val timerID: Int
    fun isStarted(): Boolean
}

internal sealed interface StartedTimerState : TimerState {
    fun asPausedTimer(): PausedTimerState
    override fun isStarted() = true
}

internal sealed interface PausedTimerState : TimerState {
    fun asStartedTimer(): StartedTimerState
    override fun isStarted() = false
}

internal sealed interface CountDownTimerState : TimerState {
    fun asHalfTime(): CountDownTimerState

    companion object
}

internal sealed interface CountUpTimerState : TimerState {
    companion object
}

@OptIn(ExperimentalTime::class)
internal class StartedCountUpTimerState(
    private val priorElapsedTime: Duration,
    private val startedTime: Instant,
    override val title: String,
    override val timerID: Int,
) : StartedTimerState, CountUpTimerState {

    private fun timeSinceStarted(): Duration {
        val now = Clock.System.now()
        return (now - startedTime)
    }

    private fun totalElapsedTime(): Duration {
        return priorElapsedTime + timeSinceStarted()
    }

    override fun asPausedTimer(): PausedTimerState {
        return PausedCountUpTimerState(totalElapsedTime(), title, timerID)
    }

    override fun showTime(): Duration {
        return totalElapsedTime()
    }

    override fun asResetTimer(): TimerState {
        return PausedCountUpTimerState(Duration.ZERO, title, timerID)
    }
}

@OptIn(ExperimentalTime::class)
internal class StartedCountDownTimerState(
    private val priorRemainingTime: Duration,
    private val startedTime: Instant,
    override val title: String,
    override val timerID: Int,
) : StartedTimerState, CountDownTimerState {

    private fun timeSinceStarted(): Duration {
        val now = Clock.System.now()
        return (now - startedTime)
    }

    private fun totalRemainingTime(): Duration {
        return priorRemainingTime - timeSinceStarted()
    }

    override fun asPausedTimer(): PausedTimerState {
        return PausedCountDownTimerState(totalRemainingTime(), title, timerID)
    }

    override fun showTime(): Duration {
        return totalRemainingTime()
    }

    override fun asResetTimer(): TimerState {
        return PausedCountDownTimerState(INITIAL_COUNT_DOWN_TIMER_DURATION, title, timerID)
    }

    override fun asHalfTime(): CountDownTimerState {
        val now = Clock.System.now()
        return StartedCountDownTimerState(totalRemainingTime() / 2, now, title, timerID)
    }
}

// TODO: secondary constructor for initial creation ?

@OptIn(ExperimentalTime::class)
internal class PausedCountUpTimerState(
    private val elapsedTime: Duration,
    override val title: String,
    override val timerID: Int,
) : PausedTimerState, CountUpTimerState {
    override fun asStartedTimer(): StartedTimerState {
        val now = Clock.System.now()
        return StartedCountUpTimerState(elapsedTime, now, title, timerID)
    }

    override fun showTime(): Duration {
        return elapsedTime
    }

    override fun asResetTimer(): TimerState {
        return PausedCountUpTimerState(Duration.ZERO, title, timerID)
    }
}

@OptIn(ExperimentalTime::class)
internal class PausedCountDownTimerState(
    private val remainingTime: Duration,
    override val title: String,
    override val timerID: Int,
) : PausedTimerState, CountDownTimerState {
    override fun asStartedTimer(): StartedTimerState {
        val now = Clock.System.now()
        return StartedCountDownTimerState(remainingTime, now, title, timerID)
    }

    override fun showTime(): Duration {
        return remainingTime
    }

    override fun asResetTimer(): TimerState {
        return PausedCountDownTimerState(INITIAL_COUNT_DOWN_TIMER_DURATION, title, timerID)
    }

    override fun asHalfTime(): CountDownTimerState {
        return PausedCountDownTimerState(remainingTime / 2, title, timerID)
    }
}
