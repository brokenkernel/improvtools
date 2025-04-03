package com.brokenkernel.improvtools

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PackageInfoFlags
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.brokenkernel.improvtools.application.data.repository.AboutScreenRepository
import com.brokenkernel.improvtools.application.data.repository.DefaultAboutScreenRepository
import com.brokenkernel.improvtools.datastore.UserSettings
import com.brokenkernel.improvtools.settings.data.repository.DefaultSettingsRespository
import com.brokenkernel.improvtools.settings.data.repository.SettingsRepository
import com.brokenkernel.improvtools.settings.data.serialisation.UserSettingsSerializer
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.AudienceSuggestionDatumRepository
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.ResourcesAudienceSuggestionDatumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

private const val USER_SETTINGS_NAME = "user_settings.pb"


// TODO: separate impl from bind
@Module
@InstallIn(ViewModelComponent::class)
internal class ViewModuleRepositoryModule {

    @Provides
//    @ViewModelScoped
    fun providesAudienceSuggestionDatumRepository(
        @ApplicationContext appContext: Context,
    ): AudienceSuggestionDatumRepository {
        return ResourcesAudienceSuggestionDatumRepository(appContext.resources)
    }

    @Provides
    fun providesAboutScreenRepository(@ApplicationContext appContext: Context): AboutScreenRepository {
        val packageManager: PackageManager = appContext.packageManager
        val packageInfo: PackageInfo = packageManager.getPackageInfo(appContext.packageName, PackageInfoFlags.of(0))
        return DefaultAboutScreenRepository(packageInfo, packageManager.isSafeMode, appContext.resources)
    }
}


@Module
@InstallIn(ActivityRetainedComponent::class)
class ApplicationDatastoreModule {

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

@Module
@InstallIn(ActivityRetainedComponent::class)
class ApplicationModule {
    @ActivityRetainedScoped
    @Provides
    fun providesSettingsRepository(datastore: DataStore<UserSettings>): SettingsRepository {
        return DefaultSettingsRespository(datastore)
    }
}