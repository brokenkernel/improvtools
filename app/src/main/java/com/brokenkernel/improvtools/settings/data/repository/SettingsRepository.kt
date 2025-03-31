package com.brokenkernel.improvtools.settings.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class UserSettings(val allowSuggestionsReuse: Boolean)

internal interface SettingsRepository {
    val userSettingsFlow: Flow<UserSettings>

    suspend fun updateAllowSuggestionsReuse(showCompleted: Boolean)
}

internal class DefaultSettingsRespository(private val userPreferenceDataStore: DataStore<Preferences>) : SettingsRepository {
    private object PreferencesKeys {
        val ALLOW_SUGGESTION_REUSE = booleanPreferencesKey("show_completed")
    }

    override val userSettingsFlow: Flow<UserSettings> = userPreferenceDataStore.data
        .map { preferences ->
            // Get our show completed value, defaulting to false if not set:
            val showCompleted = preferences[PreferencesKeys.ALLOW_SUGGESTION_REUSE]?: false
            UserSettings(showCompleted)
        }

    override suspend fun updateAllowSuggestionsReuse(allowSuggestinReUse: Boolean) {
        userPreferenceDataStore.edit { preferences ->
            preferences[PreferencesKeys.ALLOW_SUGGESTION_REUSE] = allowSuggestinReUse
        }
    }
}