package com.brokenkernel.improvtools.encyclopaedia.data.repository

import com.brokenkernel.improvtools.encyclopaedia.data.DictionaryInfo

internal class DefaultThesaurusRepository(private val dictionaryInfoI: DictionaryInfo) : ThesaurusRepository {

    override fun getDictionaryInfo(): DictionaryInfo {
        return dictionaryInfoI
    }
}
