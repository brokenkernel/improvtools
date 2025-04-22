package com.brokenkernel.improvtools.encyclopaedia.api

import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import com.brokenkernel.improvtools.encyclopaedia.presentation.view.ThesaurusTabSingleWord
import javax.inject.Inject

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
}
