package com.brokenkernel.improvtools.encyclopaedia.data

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet

public class MergedDictionaryInfo internal constructor(
    private val extJwnlDictionaryInfo: ExtJwnlDictionaryInfo,
    private val actionWordsDictionaryInfo: ActionWordsDictionaryInfo,
) : DictionaryInfo {
    override fun hasWordInfo(word: String): Boolean {
        return extJwnlDictionaryInfo.hasWordInfo(word) || actionWordsDictionaryInfo.hasWordInfo(word)
    }

    override fun synonymsForWord(word: String): ImmutableSet<String> {
        val r = extJwnlDictionaryInfo.synonymsForWord(word) + actionWordsDictionaryInfo.synonymsForWord(word)
        return r.toImmutableSet()
    }

    /**
     * This really shouldn't exist and only exists since callers are weird.
     * It makes it hard to get more general getSynonyms usefully.
     * Leave for now until API can be properly fixed
     */
    override fun getSynonymsPOSMap(word: String): Map<String, List<WordInfo>> {
        return extJwnlDictionaryInfo.getSynonymsPOSMap(word) +
            actionWordsDictionaryInfo.getSynonymsPOSMap(word)
    }

    override fun getWordsByType(wordtype: WordType): ImmutableSet<String> {
        val r: Set<String> = extJwnlDictionaryInfo.getWordsByType(wordtype) +
            actionWordsDictionaryInfo.getWordsByType(wordtype)
        return r.toImmutableSet()
    }

    public companion object {
        public fun create(): MergedDictionaryInfo {
            return MergedDictionaryInfo(
                ExtJwnlDictionaryInfo(),
                ActionWordsDictionaryInfo(),
            )
        }
    }
}
