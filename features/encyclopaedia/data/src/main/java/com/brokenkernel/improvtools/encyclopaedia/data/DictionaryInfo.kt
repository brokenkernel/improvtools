package com.brokenkernel.improvtools.encyclopaedia.data

import kotlinx.collections.immutable.ImmutableSet

public interface DictionaryInfo {
    public fun hasWordInfo(word: String): Boolean
    public fun synonymsForWord(word: String): ImmutableSet<String>
    public fun getSynonymsPOSMap(word: String): Map<String, List<WordInfo>>
    public fun getWordsByType(wordtype: WordType): ImmutableSet<String>
}
