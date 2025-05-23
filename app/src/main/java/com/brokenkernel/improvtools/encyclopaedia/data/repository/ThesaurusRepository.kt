package com.brokenkernel.improvtools.encyclopaedia.data.repository

import com.brokenkernel.improvtools.encyclopaedia.data.DictionaryInfo
import com.brokenkernel.improvtools.encyclopaedia.data.model.ActionsThesaurus

internal interface ThesaurusRepository {
    fun getActionsThesaurus(): ActionsThesaurus
    fun getMergedDictionaryInfo(): DictionaryInfo
}
