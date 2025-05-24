package com.brokenkernel.improvtools.encyclopaedia.data

import androidx.collection.LruCache
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableSet
import net.sf.extjwnl.data.IndexWordSet
import net.sf.extjwnl.data.Synset
import net.sf.extjwnl.dictionary.Dictionary

internal class ExtJwnlDictionaryInfo : DictionaryInfo {
    private val dictionary: Dictionary = Dictionary.getDefaultResourceInstance()
    private val lookupAllWordCache: LruCache<String, IndexWordSet> =
        LruCache(1000) // arbitrarily picked

    private fun lookupWordCached(preppedWord: String): IndexWordSet {
        val cachedResult: IndexWordSet? = lookupAllWordCache[preppedWord]
        if (cachedResult == null) {
            val allIndexWords: IndexWordSet = dictionary.lookupAllIndexWords(preppedWord)
            lookupAllWordCache.put(preppedWord, allIndexWords)
            return allIndexWords
        } else {
            return cachedResult
        }
    }

    override fun hasWordInfo(word: String): Boolean {
        val preppedWord = word.trim().lowercase()
        val allIndexWords: IndexWordSet = lookupWordCached(preppedWord)
        if (allIndexWords.size() == 0) {
            return false
        }
        // TODO: I don't actually think this works... I want roughly exact match
        return allIndexWords.indexWordCollection.orEmpty().any {
            it.lemma == preppedWord
        }
    }

    override fun synonymsForWord(word: String): ImmutableSet<String> {
        // TODO: THIS IS BROKEN
        return persistentSetOf()
    }

    override fun getSynonymsPOSMap(word: String): Map<String, List<WordInfo>> {
        val preppedWord = word.trim().lowercase()
        val allIndexWords: IndexWordSet = lookupWordCached(preppedWord)
        val resultMap: Map<String, List<WordInfo>> = allIndexWords.validPOSSet.associate { pos ->
            val indexWord = allIndexWords.getIndexWord(pos)
            indexWord.sortSenses()
            val senses: List<Synset> = indexWord.senses.orEmpty()

            val subSenses = senses.map { sense: Synset ->
                val glossSplit = sense.gloss.split(";", limit = 2)
                val glossPrimary = glossSplit.getOrElse(0, { "" })
                val glossSecondary = glossSplit.getOrElse(1, { "" })

                StandardWordInfo(
                    lemma = indexWord.lemma,
                    description = glossPrimary,
                    example = glossSecondary,
                    synonyms = sense.words.map { w -> w.lemma }.toImmutableSet(),
                )
            }
            pos.label to subSenses
        }
        return resultMap
    }

    override fun getWordsByType(wordtype: WordType): ImmutableSet<String> {
        if (wordtype.internalPOS == null) {
            return persistentSetOf()
        }
        return dictionary
            .getIndexWordIterator(wordtype.internalPOS)
            .asSequence()
            .map { it.lemma }
            .toImmutableSet()
    }
}
