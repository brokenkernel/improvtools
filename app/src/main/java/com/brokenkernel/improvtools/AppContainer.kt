package com.brokenkernel.improvtools

import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.settings.data.repository.DefaultSettingsRespository
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.AudienceSuggestionDatumRepository
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.ResourcesAudienceSuggestionDatumRepository

internal interface AppContainer {
    val audienceSugestionDatumRespository: AudienceSuggestionDatumRepository
    val settingsRepository: SettingsRepository
}

internal class DefaultAppContainer(
    resources: Resources,
    userPreferenceDataStore: DataStore<UserSettings>
) : AppContainer {

    override val audienceSugestionDatumRespository: AudienceSuggestionDatumRepository by lazy {
        ResourcesAudienceSuggestionDatumRepository(resources)
    }
    override val settingsRepository: SettingsRepository by lazy {
        DefaultSettingsRespository(userPreferenceDataStore)
    }

}