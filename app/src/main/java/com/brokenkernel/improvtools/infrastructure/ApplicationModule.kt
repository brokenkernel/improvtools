package com.brokenkernel.improvtools.infrastructure

import androidx.datastore.core.DataStore
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.settings.data.repository.DefaultSettingsRespository
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ApplicationModule {
    @Singleton
    @Provides
    fun providesSettingsRepository(datastore: DataStore<UserSettings>): SettingsRepository {
        return DefaultSettingsRespository(datastore)
    }
}