package com.brokenkernel.improvtools.encyclopaedia.data

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableSet

internal class ActionWordsDictionaryInfo : DictionaryInfo {
    override fun hasWordInfo(word: String): Boolean {
        return RawActionItemDataList.asSynonymMap.contains(word)
    }

    override fun getSynonymsPOSMap(word: String): Map<String, List<WordInfo>> {
        // TODO: this is really broken, but it doesn't matter and this API needs to be removed
        return persistentMapOf()
    }

    override fun getWordsByType(wordtype: WordType): ImmutableSet<String> {
        if (wordtype != WordType.ACTION) {
            return persistentSetOf()
        } else {
            return RawActionItemDataList.asSynonymMap.keys
        }
    }

    override fun synonymsForWord(word: String): ImmutableSet<String> {
        return RawActionItemDataList.asSynonymMap[word].orEmpty().toImmutableSet()
    }
}
