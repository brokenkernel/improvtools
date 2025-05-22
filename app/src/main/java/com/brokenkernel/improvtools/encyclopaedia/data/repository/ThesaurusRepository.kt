package com.brokenkernel.improvtools.encyclopaedia.data.repository

import com.brokenkernel.improvtools.encyclopaedia.data.DictionaryInfo
import com.brokenkernel.improvtools.encyclopaedia.data.model.ActionsThesaurus
import net.sf.extjwnl.dictionary.Dictionary

internal interface ThesaurusRepository {
    fun getActionsThesaurus(): ActionsThesaurus
    fun getEXTJWNLDictionary(): Dictionary
    fun getMergedDictionaryInfo(): DictionaryInfo
}
