package com.brokenkernel.improvtools.settings.data.repository

import androidx.compose.runtime.Immutable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Immutable
data class UserSettings(val allowSuggestionsReuse: Boolean)

internal interface SettingsRepository {
    val userSettingsFlow: Flow<UserSettings>

    suspend fun updateAllowSuggestionsReuse(showCompleted: Boolean)
}

internal class DefaultSettingsRespository(private val userPreferenceDataStore: DataStore<Preferences>) :
    SettingsRepository {
    private object PreferencesKeys {
        val ALLOW_SUGGESTION_REUSE = booleanPreferencesKey("show_completed")
    }

    fun shouldAllowSuggestionReuse(): Flow<Boolean> =
        userPreferenceDataStore.data.map { preferences ->
            val allowSuggestionsReuse = preferences[PreferencesKeys.ALLOW_SUGGESTION_REUSE] ?: false
            allowSuggestionsReuse
        }


    override val userSettingsFlow: Flow<UserSettings> =
        userPreferenceDataStore.data.map { preferences ->
            // Get our show completed value, defaulting to false if not set:
            val allowSuggestionsReuse = preferences[PreferencesKeys.ALLOW_SUGGESTION_REUSE] ?: false
            UserSettings(allowSuggestionsReuse = allowSuggestionsReuse)
        }

    override suspend fun updateAllowSuggestionsReuse(allowSuggestinReUse: Boolean) {
        userPreferenceDataStore.edit { preferences ->
            preferences[PreferencesKeys.ALLOW_SUGGESTION_REUSE] = allowSuggestinReUse
        }
    }
}