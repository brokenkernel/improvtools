package com.brokenkernel.improvtools.settings.data.repository

import androidx.datastore.core.DataStore
import com.brokenkernel.improvtools.datastore.UserSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val userSettingsFlow: Flow<UserSettings>

    suspend fun updateAllowSuggestionsReuse(allowSuggestinReUse: Boolean)
}

class DefaultSettingsRespository(private val userPreferenceDataStore: DataStore<UserSettings>) :
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
}