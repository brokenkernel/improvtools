package com.brokenkernel.improvtools.encyclopaedia.data

import kotlinx.collections.immutable.ImmutableSet

public class MergedDictionaryInfo internal constructor(
    private val extJwnlDictionaryInfo: ExtJwnlDictionaryInfo,
) : DictionaryInfo {
    override fun hasWordInfo(word: String): Boolean {
        return extJwnlDictionaryInfo.hasWordInfo(word)
    }

    /**
     * This really shouldn't exist and only exists since callers are weird.
     * It makes it hard to get more general getSynonyms usefully.
     * Leave for now until API can be properly fixed
     */
    override fun getSynonymsPOSMap(word: String): Map<String, List<WordInfo>> {
        return extJwnlDictionaryInfo.getSynonymsPOSMap(word)
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
