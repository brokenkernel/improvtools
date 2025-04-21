package com.brokenkernel.improvtools.timer.data.model

internal enum class TimerState {
    STARTED,
    PAUSED,
    STOPPED,
    ;

    fun isStarted(): Boolean {
        return this == STARTED
    }
}
