package com.brokenkernel.improvtools.settings.data.repository

import androidx.datastore.core.DataStore
import androidx.lifecycle.asLiveData
import com.brokenkernel.improvtools.MainActivity
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.infrastructure.ConsentManagement.configureConsentForFirebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
}