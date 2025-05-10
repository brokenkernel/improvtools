package com.brokenkernel.improvtools.timer.data.model

import com.brokenkernel.improvtools.datastore.UserSettings.TimerHapticsMode

enum class TimerHapticModeUI(
    val internalEnumsMatching: Set<TimerHapticsMode>,
) {
    ALWAYS(setOf(TimerHapticsMode.TIMER_HAPTICS_MODE_DEFAULT, TimerHapticsMode.TIMER_HAPTICS_MODE_ALL)),
    NONE(setOf(TimerHapticsMode.TIMER_HAPTICS_MODE_NONE)),
}
