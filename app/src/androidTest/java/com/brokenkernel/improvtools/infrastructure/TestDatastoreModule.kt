package com.brokenkernel.improvtools.infrastructure

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.settings.data.serialisation.UserSettingsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApplicationDatastoreModule::class],
)
class TestDatastoreModule {

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    @Singleton
    @Provides
    fun providesDatastoreForUserSettings(
        @ApplicationContext appContext: Context,
    ): DataStore<UserSettings> {
        val datastore: DataStore<UserSettings> = DataStoreFactory.create(
            serializer = UserSettingsSerializer,
            produceFile = {
                appContext.dataStoreFile(
                    "datastore_for_user_settings_" + getRandomString(12),
                )
            },
        )
        return datastore
    }
}
