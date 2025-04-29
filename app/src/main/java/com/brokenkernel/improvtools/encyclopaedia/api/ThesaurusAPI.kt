package com.brokenkernel.improvtools.encyclopaedia.api

import android.util.Log
import com.brokenkernel.improvtools.TAG
import com.brokenkernel.improvtools.encyclopaedia.data.model.SenseDatumUI
import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.ThesaurusTabSingleWord
import javax.inject.Inject
import net.sf.extjwnl.data.IndexWordSet
import net.sf.extjwnl.data.Synset
import net.sf.extjwnl.dictionary.Dictionary

/**
 * This is the portion of the Thesaurus exposed to other components.
 */
class ThesaurusAPI @Inject internal constructor(private val thesaurusRepository: ThesaurusRepository) {
    val dictionary: Dictionary? = thesaurusRepository.getEXTJWNLDictionary()

    /**
     * Get the set of words for which there is an entry. If the word is here,
     * navigating to [[ThesaurusTabSingleWord]] is expected to succeed.
     * @return all legal words for the Thesaurus
     */
    fun getActionWords(): Set<String> {
        return thesaurusRepository.getActionsThesaurus().keys()
    }

    // TODO: lookupAllIndexWords cache and similar - https://developer.android.com/reference/android/util/LruCache

    /**
     * Checks if a specific word has any information in the extjwnl (or other databases)
     * @param word
     */
    // TODO: check for exact match and/or stemmed match
    fun hasWordDetails(word: String): Boolean {
        val preppedWord = word.trim().lowercase()
        val allIndexWords: IndexWordSet? = dictionary?.lookupAllIndexWords(preppedWord)
        Log.w(TAG, "$preppedWord: hasWordDetails: ${allIndexWords?.toString()}")

        // TODO: deal with stemming
        if (allIndexWords?.size() == 0) {
            return false
        }
        return allIndexWords?.indexWordCollection.orEmpty().any {
            it.lemma == preppedWord
        }
    }

    fun getSenseDatum(word: String): Map<String, List<SenseDatumUI>>? {
        val allIndexWords: IndexWordSet? = dictionary?.lookupAllIndexWords(word)
        if (allIndexWords == null) {
            return null
        }
        val resultMap: Map<String, List<SenseDatumUI>> = allIndexWords.validPOSSet.associate { pos ->
            val indexWord = allIndexWords.getIndexWord(pos)!!
            indexWord.sortSenses()
            val senses: List<Synset> = indexWord.senses.orEmpty()

            val subSenses = senses.map { sense: Synset ->
                val glossSplit = sense.gloss.split(";", limit = 2)
                val glossPrimary = glossSplit.getOrElse(0, { "" })
                val glossSecondary = glossSplit.getOrElse(1, { "" })

                SenseDatumUI(
                    lemma = indexWord.lemma,
                    description = glossPrimary,
                    example = glossSecondary,
                    synonyms = sense.words.map { w -> w.lemma },
                )
            }
            pos.label to subSenses
        }
        return resultMap
    }
}
