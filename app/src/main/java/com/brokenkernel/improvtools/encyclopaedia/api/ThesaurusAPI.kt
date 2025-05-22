package com.brokenkernel.improvtools.encyclopaedia.api

import com.brokenkernel.improvtools.encyclopaedia.data.DictionaryInfo
import com.brokenkernel.improvtools.encyclopaedia.data.WordInfo
import com.brokenkernel.improvtools.encyclopaedia.data.model.SenseDatumUI
import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.ThesaurusTabSingleWord
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList

/**
 * This is the portion of the Thesaurus exposed to other components.
 */
class ThesaurusAPI @Inject internal constructor(private val thesaurusRepository: ThesaurusRepository) {
    val dictionary: DictionaryInfo = thesaurusRepository
        .getMergedDictionaryInfo()

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
        return dictionary
            .hasWordInfo(preppedWord)
    }

    fun getSenseDatum(word: String): Map<String, List<SenseDatumUI>> {
        val preppedWord = word.trim().lowercase()
        val wordInfoMap: Map<String, List<WordInfo>> = dictionary
            .getSynonyms(preppedWord)
        val uxWordInfoMap: Map<String, List<SenseDatumUI>> = wordInfoMap.mapValues { (_, v) ->
            v.map { i ->
                SenseDatumUI(
                    lemma = i.lemma,
                    description = i.description,
                    example = i.example,
                    synonyms = i.synonyms.toImmutableList(),
                )
            }
        }
        return uxWordInfoMap
    }
}
