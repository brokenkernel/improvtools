package com.brokenkernel.improvtools.encyclopaedia.data

public class MergedDictionaryInfo internal constructor(
    private val extJwnlDictionaryInfo: ExtJwnlDictionaryInfo,
) : DictionaryInfo {
    override fun hasWordInfo(word: String): Boolean {
        return extJwnlDictionaryInfo.hasWordInfo(word)
    }

    override fun getSynonyms(word: String): Map<String, List<WordInfo>> {
        return extJwnlDictionaryInfo.getSynonyms(word)
    }

    public companion object {
        public fun create(): MergedDictionaryInfo {
            return MergedDictionaryInfo(
                ExtJwnlDictionaryInfo(),
            )
        }
    }
}
