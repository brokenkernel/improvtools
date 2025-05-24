package com.brokenkernel.improvtools.timer.model

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

public sealed interface TimerState {
    /**
     * This is the time to show on on the screen.
     */
    public fun showTime(): Duration
    public fun asResetTimer(): TimerState // really irksome there are no true traits.
    public val title: String
    public val timerID: Int
    public fun isStarted(): Boolean

    public fun asEdited(
        title: String = this.title,
    ): TimerState
}

public sealed interface StartedTimerState : TimerState {
    public fun asPausedTimer(): PausedTimerState
    override fun isStarted(): Boolean = true
}

public sealed interface PausedTimerState : TimerState {
    public fun asStartedTimer(): StartedTimerState
    override fun isStarted(): Boolean = false
}

public sealed interface CountDownTimerState : TimerState {
    public fun asHalfTime(): CountDownTimerState

    public companion object
}

public sealed interface CountUpTimerState : TimerState {
    public companion object
}

@OptIn(ExperimentalTime::class)
public class StartedCountUpTimerState(
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
