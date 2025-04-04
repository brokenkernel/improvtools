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
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

private const val USER_SETTINGS_NAME = "user_settings.pb"

@Module
@InstallIn(ActivityRetainedComponent::class)
internal class ApplicationDatastoreModule {

    @ActivityRetainedScoped
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