package com.brokenkernel.improvtools.encyclopaedia.data.model

import com.brokenkernel.improvtools.encyclopaedia.data.DictionaryInfo

internal class ActionsThesaurus(val mergedDictionaryInfo: DictionaryInfo) {
    fun synonymsForWord(word: String): Set<String> {
        return mergedDictionaryInfo.synonymsForWord(word)
    }
}
