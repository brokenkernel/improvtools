package com.brokenkernel.improvtools.encyclopaedia.data.repository

import com.brokenkernel.improvtools.encyclopaedia.data.DictionaryInfo

internal interface ThesaurusRepository {
    fun getDictionaryInfo(): DictionaryInfo
}
