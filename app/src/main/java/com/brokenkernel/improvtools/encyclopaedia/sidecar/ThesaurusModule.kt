package com.brokenkernel.improvtools.encyclopaedia.sidecar

import com.brokenkernel.improvtools.encyclopaedia.data.repository.DefaultThesaurusRepository
import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class ThesaurusModule {

    @Provides
    fun providesThesaurusRepository(): ThesaurusRepository {
        return DefaultThesaurusRepository()
    }
}
