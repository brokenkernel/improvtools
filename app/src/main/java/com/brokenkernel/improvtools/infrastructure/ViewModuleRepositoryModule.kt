package com.brokenkernel.improvtools.infrastructure

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PackageInfoFlags
import com.brokenkernel.improvtools.application.data.repository.AboutScreenRepository
import com.brokenkernel.improvtools.application.data.repository.DefaultAboutScreenRepository
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.AudienceSuggestionDatumRepository
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.ResourcesAudienceSuggestionDatumRepository
import com.brokenkernel.improvtools.tipsandadvice.data.repository.DefaultTipsAndAdviceRepository
import com.brokenkernel.improvtools.tipsandadvice.data.repository.TipsAndAdviceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

// TODO: separate impl from bind
@Module
@InstallIn(ViewModelComponent::class)
internal class ViewModuleRepositoryModule {

    @Provides
    fun providesTipsAndAdviceRepository(
        @ApplicationContext appContext: Context,
    ): TipsAndAdviceRepository {
        return DefaultTipsAndAdviceRepository(appContext.resources)
    }

    @Provides
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