package com.brokenkernel.improvtools.settings.presentation.uistate

import com.brokenkernel.improvtools.datastore.UserSettings

internal data class SettingsScreenUIState(
    val shouldReuseSuggestions: Boolean,
    val allowAnalyticsCookieStorage: Boolean,
    val timerHapticsMode: UserSettings.TimerHapticsMode,
) {
    companion object {
        fun default(): SettingsScreenUIState {
            return SettingsScreenUIState(
                shouldReuseSuggestions = false,
                allowAnalyticsCookieStorage = true,
                timerHapticsMode = UserSettings.TimerHapticsMode.TIMER_HAPTICS_MODE_DEFAULT,
            )
        }
    }
}
