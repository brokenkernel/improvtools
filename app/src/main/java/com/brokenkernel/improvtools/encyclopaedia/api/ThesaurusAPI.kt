package com.brokenkernel.improvtools.encyclopaedia.api

import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.ThesaurusTabSingleWord
import javax.inject.Inject
import net.sf.extjwnl.data.IndexWord
import net.sf.extjwnl.data.POS
import net.sf.extjwnl.data.Synset
import net.sf.extjwnl.dictionary.Dictionary

/**
 * This is the portion of the Thesaurus exposed to other components.
 */
class ThesaurusAPI @Inject internal constructor(private val thesaurusRepository: ThesaurusRepository) {
    /**
     * Get the set of words for which there is an entry. If the word is here,
     * navigating to [[ThesaurusTabSingleWord]] is expected to succeed.
     * @return all legal words for the Thesaurus
     */
    fun getActionWords(): Set<String> {
        return thesaurusRepository.getActionsThesaurus().keys()
    }

    /**
     * Checks if a specific word has any information in the extjwnl (or other databases)
     * @param word
     */
    fun hasWordDetailsForVerb(word: String): Boolean {
        val dictionary = Dictionary.getDefaultResourceInstance()
        val indexWord: IndexWord? = dictionary.lookupIndexWord(POS.VERB, word)
        return indexWord != null
    }

    // TODO: this really should return model information and something else should render it ...
    fun getWordSensesFullyRenderedStringForVerb(word: String): String? {
        val dictionary = Dictionary.getDefaultResourceInstance()
        val indexWord: IndexWord? = dictionary.lookupIndexWord(POS.VERB, word)
        if (indexWord == null) {
            return null
        }
        val senses: List<Synset> = indexWord.senses.orEmpty()

        val debugDatum: String = buildString {
            append("<ul>")
            senses.forEach { sense: Synset ->
                append("<li>")
                val glossSplit = sense.gloss.split(";", limit = 2)
                val glossPrimary = glossSplit.getOrElse(0, { "..." })
                val glossSecondary = glossSplit.getOrElse(1, { "..." })
                append("<u>$glossPrimary</u>")
                append("<i>$glossSecondary</i>")
                append("<ul>")
                sense.words.forEach { subword ->
                    append("<li>${subword.lemma}</li>")
                }
                append("</ul>")
                append("</li>")
            }
            append("</ul>")
        }

        return debugDatum
    }
}
