package com.brokenkernel.improvtools.infrastructure

import android.content.Context
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
}
