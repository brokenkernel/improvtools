package com.brokenkernel.improvtools.encyclopaedia.data.repository

import com.brokenkernel.improvtools.encyclopaedia.data.DictionaryInfo
import com.brokenkernel.improvtools.encyclopaedia.data.MergedDictionaryInfo
import com.brokenkernel.improvtools.encyclopaedia.data.model.ActionsThesaurus

internal class DefaultThesaurusRepository : ThesaurusRepository {
    val mergedDictionaryInfo = MergedDictionaryInfo.create()

    override fun getActionsThesaurus(): ActionsThesaurus {
        return ActionsThesaurus
    }

    override fun getMergedDictionaryInfo(): DictionaryInfo {
        return mergedDictionaryInfo
    }
}
