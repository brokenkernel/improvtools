package com.brokenkernel.improvtools.infrastructure

import android.content.Context
import com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.repository.DefaultTipsAndAdviceRepository
import com.brokenkernel.improvtools.encyclopaedia.android.tipsandadvice.repository.TipsAndAdviceRepository
import com.brokenkernel.improvtools.suggestions.repository.NamesAudienceSuggestionDatumRepository
import com.brokenkernel.improvtools.suggestions.repository.ResourcesAudienceSuggestionDatumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

// TODO: separate impl from bind
@Module
@InstallIn(ViewModelComponent::class)
internal class ViewModuleRepositoryModule {

    // TODO: move to tips-and-advice repository
    @Provides
    fun providesTipsAndAdviceRepository(
        @ApplicationContext appContext: Context,
    ): TipsAndAdviceRepository {
        return DefaultTipsAndAdviceRepository(appContext.resources)
    }

    // TODO: move to suggestions repository
    @Provides
    fun providesAudienceSuggestionDatumRepository(
        @ApplicationContext appContext: Context,
    ): ResourcesAudienceSuggestionDatumRepository {
        return ResourcesAudienceSuggestionDatumRepository(appContext.resources)
    }

    // TODO: move to suggestions repository
    @Provides
    fun providesNamesSuggestionDatumRepository(
        @ApplicationContext appContext: Context,
    ): NamesAudienceSuggestionDatumRepository {
        return NamesAudienceSuggestionDatumRepository(appContext.resources)
    }
}
