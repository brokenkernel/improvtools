package com.brokenkernel.improvtools.settings.presentation.uistate

import androidx.compose.runtime.Immutable
import com.brokenkernel.improvtools.datastore.UserSettings

@Immutable
internal data class SettingsScreenUIState(
    val shouldReuseSuggestions: Boolean,
    val allowAnalyticsCookieStorage: Boolean,
    val tipsAndTricksViewMode: UserSettings.TipsAndTricksViewMode,
) {
    companion object {
        fun default(): SettingsScreenUIState {
            return SettingsScreenUIState(
                shouldReuseSuggestions = false,
                allowAnalyticsCookieStorage = true,
                tipsAndTricksViewMode = UserSettings.TipsAndTricksViewMode.VIEW_MODE_DEFAULT
            )
        }
    }
}
