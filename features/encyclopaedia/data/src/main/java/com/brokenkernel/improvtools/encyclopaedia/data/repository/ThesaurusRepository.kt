package com.brokenkernel.improvtools.encyclopaedia.data.repository

import com.brokenkernel.improvtools.encyclopaedia.data.DictionaryInfo

public interface ThesaurusRepository {
    public fun getDictionaryInfo(): DictionaryInfo
}
