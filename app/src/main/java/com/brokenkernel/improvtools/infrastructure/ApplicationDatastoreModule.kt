package com.brokenkernel.improvtools.infrastructure

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.settings.data.serialisation.UserSettingsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_SETTINGS_NAME = "user_settings.pb"

@Module
@InstallIn(SingletonComponent::class)
internal class ApplicationDatastoreModule {

    @Singleton
    @Provides
    fun providesDatastoreForUserSettings(
        @ApplicationContext appContext: Context,
    ): DataStore<UserSettings> {
        val datastore: DataStore<UserSettings> = DataStoreFactory.create(
            serializer = UserSettingsSerializer,
            produceFile = { appContext.dataStoreFile(USER_SETTINGS_NAME) }

        )
        return datastore
    }
}