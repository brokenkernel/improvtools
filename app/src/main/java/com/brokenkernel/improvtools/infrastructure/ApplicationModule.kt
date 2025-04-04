package com.brokenkernel.improvtools.infrastructure

import androidx.datastore.core.DataStore
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.settings.data.repository.DefaultSettingsRespository
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
internal class ApplicationModule {
    @ActivityRetainedScoped
    @Provides
    fun providesSettingsRepository(datastore: DataStore<UserSettings>): SettingsRepository {
        return DefaultSettingsRespository(datastore)
    }
}