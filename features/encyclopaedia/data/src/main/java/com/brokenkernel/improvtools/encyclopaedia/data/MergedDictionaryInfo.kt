package com.brokenkernel.improvtools.encyclopaedia.data

import kotlinx.collections.immutable.ImmutableSet

public class MergedDictionaryInfo internal constructor(
    private val extJwnlDictionaryInfo: ExtJwnlDictionaryInfo,
) : DictionaryInfo {
    override fun hasWordInfo(word: String): Boolean {
        return extJwnlDictionaryInfo.hasWordInfo(word)
    }

    override fun getSynonyms(word: String): Map<String, List<WordInfo>> {
        return extJwnlDictionaryInfo.getSynonyms(word)
    }

    override fun getWordsByType(wordtype: WordType): ImmutableSet<String> {
        return extJwnlDictionaryInfo.getWordsByType(wordtype)
    }

    public companion object {
        public fun create(): MergedDictionaryInfo {
            return MergedDictionaryInfo(
                ExtJwnlDictionaryInfo(),
            )
        }
    }
}
