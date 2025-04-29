package com.brokenkernel.improvtools.encyclopaedia.api

import com.brokenkernel.improvtools.encyclopaedia.data.model.SenseDatumUI
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
    val dictionary: Dictionary? = thesaurusRepository.getEXTJWNLDictionary()

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
        val indexWord: IndexWord? = dictionary?.lookupIndexWord(POS.VERB, word)
        return indexWord != null
    }

    // TODO: this really should return model information and something else should render it ...
    fun getSenseDatumForVerb(word: String): List<SenseDatumUI>? {
        val indexWord: IndexWord? = dictionary?.lookupIndexWord(POS.VERB, word)
        if (indexWord == null) {
            return null
        }
        val senses: List<Synset> = indexWord.senses.orEmpty()

        return senses.map { sense: Synset ->
            val glossSplit = sense.gloss.split(";", limit = 2)
            val glossPrimary = glossSplit.getOrElse(0, { "" })
            val glossSecondary = glossSplit.getOrElse(1, { "" })

            SenseDatumUI(
                description = glossPrimary,
                example = glossSecondary,
                synonyms = sense.words.map { w -> w.lemma },
            )
        }.toList()
    }
}
