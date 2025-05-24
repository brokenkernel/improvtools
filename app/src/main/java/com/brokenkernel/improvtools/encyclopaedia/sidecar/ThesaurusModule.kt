package com.brokenkernel.improvtools.encyclopaedia.sidecar

import com.brokenkernel.improvtools.encyclopaedia.data.DictionaryInfo
import com.brokenkernel.improvtools.encyclopaedia.data.MergedDictionaryInfo
import com.brokenkernel.improvtools.encyclopaedia.data.repository.DefaultThesaurusRepository
import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ThesaurusModule {

    @Provides
    @Singleton
    fun providesThesaurusRepository(dictionaryInfo: DictionaryInfo): ThesaurusRepository {
        return DefaultThesaurusRepository(dictionaryInfo)
    }

    @Provides
    @Singleton
    fun providesDictionaryInfo(): DictionaryInfo {
        return MergedDictionaryInfo.create()
    }
}
