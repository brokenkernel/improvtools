package com.brokenkernel.improvtools.settings.presentation.uistate

import androidx.compose.runtime.Immutable

@Immutable
internal data class SettingsScreenUIState(
    val shouldReuseSuggestions: Boolean,
    val allowAnalyticsCookieStorage: Boolean
) {
    companion object {
        fun default(): SettingsScreenUIState {
            return SettingsScreenUIState(shouldReuseSuggestions = false, allowAnalyticsCookieStorage = true)
        }
    }
}
