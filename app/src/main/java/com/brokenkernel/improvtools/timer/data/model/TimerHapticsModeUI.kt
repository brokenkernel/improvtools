package com.brokenkernel.improvtools.timer.data.model

import com.brokenkernel.improvtools.datastore.UserSettings.TimerHapticsMode

enum class TimerHapticsModeUI(
    val internalEnumMatching: TimerHapticsMode,
) {
    ALWAYS(TimerHapticsMode.TIMER_HAPTICS_MODE_ALL),
    NONE(TimerHapticsMode.TIMER_HAPTICS_MODE_NONE),
}
