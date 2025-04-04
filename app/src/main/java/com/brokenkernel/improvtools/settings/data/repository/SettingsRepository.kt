package com.brokenkernel.improvtools.settings.data.repository

import com.brokenkernel.improvtools.datastore.UserSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val userSettingsFlow: Flow<UserSettings>

    suspend fun updateAllowSuggestionsReuse(allowSuggestinReUse: Boolean)
}

