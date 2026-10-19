package com.brokenkernel.improvtools.encyclopaedia.android.sidecar

import com.brokenkernel.improvtools.encyclopaedia.data.DictionaryInfo
import com.brokenkernel.improvtools.encyclopaedia.data.createDictionaryInfo
import com.brokenkernel.improvtools.encyclopaedia.data.repository.DefaultThesaurusRepository
import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public object ThesaurusModule {

    @Provides
    @Singleton
    public fun providesThesaurusRepository(dictionaryInfo: DictionaryInfo): ThesaurusRepository {
        return DefaultThesaurusRepository(dictionaryInfo)
    }

    @Provides
    @Singleton
    public fun providesDictionaryInfo(): DictionaryInfo {
        return createDictionaryInfo()
    }
}
