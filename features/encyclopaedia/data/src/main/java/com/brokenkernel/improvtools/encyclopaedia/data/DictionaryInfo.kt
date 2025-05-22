package com.brokenkernel.improvtools.encyclopaedia.data

public interface DictionaryInfo {
    public fun hasWordInfo(word: String): Boolean
    public fun getSynonyms(word: String): Map<String, List<WordInfo>>
}
