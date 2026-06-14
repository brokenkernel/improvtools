package com.brokenkernel.improvtools.timer.data.model

import com.brokenkernel.improvtools.datastore.UserSettings.TimerHapticsMode

public enum class TimerHapticsModeUI(
    public val internalEnumMatching: TimerHapticsMode,
) {
    ALWAYS(TimerHapticsMode.TIMER_HAPTICS_MODE_ALL),
    NONE(TimerHapticsMode.TIMER_HAPTICS_MODE_NONE),
}
