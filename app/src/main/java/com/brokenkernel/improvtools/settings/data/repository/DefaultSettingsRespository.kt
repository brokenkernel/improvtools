package com.brokenkernel.improvtools.settings.data.repository

import androidx.datastore.core.DataStore
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.datastore.UserSettings.TipsAndTricksViewMode
import kotlinx.coroutines.flow.Flow

internal class DefaultSettingsRespository(private val userPreferenceDataStore: DataStore<UserSettings>) :
    SettingsRepository {

    override val userSettingsFlow: Flow<UserSettings> =
        userPreferenceDataStore.data

    override suspend fun updateAllowSuggestionsReuse(allowSuggestinReUse: Boolean) {
        userPreferenceDataStore.updateData { preferences ->
            preferences.toBuilder()
                .setAllowSuggestionsReuse(allowSuggestinReUse)
                .build()
        }
    }

    override suspend fun updateAllowAnalyticsCookieStorage(allowAnalyticsCookieStorage: Boolean) {
        userPreferenceDataStore.updateData { preferences ->
            preferences.toBuilder()
                .setAllowAnalyticsCookieStorage(allowAnalyticsCookieStorage)
                .build()
        }
    }

    override suspend fun updateTipsAndTricksViewMode(tipsAndTricksViewMode: TipsAndTricksViewMode) {
        userPreferenceDataStore.updateData { preferences ->
            preferences.toBuilder()
                .setTipsAndTricksViewMode(tipsAndTricksViewMode)
                .build()
        }
    }

    override suspend fun updateTimerHapticsMode(timerHapticsMode: UserSettings.TimerHapticsMode) {
        userPreferenceDataStore.updateData { preferences ->
            preferences.toBuilder()
                .setHapticFeedbackTimerMode(timerHapticsMode)
                .build()
        }
    }

}