package com.brokenkernel.improvtools.settings.presentation.uistate

import com.brokenkernel.improvtools.datastore.UserSettings

internal data class SettingsScreenUIState(
    val allowAnalyticsCookieStorage: Boolean,
    val timerHapticsMode: UserSettings.TimerHapticsMode,
) {
    companion object {
        fun default(): SettingsScreenUIState {
            return SettingsScreenUIState(
                allowAnalyticsCookieStorage = true,
                timerHapticsMode = UserSettings.TimerHapticsMode.TIMER_HAPTICS_MODE_DEFAULT,
            )
        }
    }
}
