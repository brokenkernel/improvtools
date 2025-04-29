package com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.encyclopaedia.api.ThesaurusAPI
import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ThesaurusTabAllItemsViewModel @Inject constructor(
    val thesaurusRepository: ThesaurusRepository,
    val thesaurusAPI: ThesaurusAPI, // todo: this should be accessed through a repository
) : ViewModel() {
    fun words(): List<String> {
        return thesaurusRepository.getActionsThesaurus().keys().sorted().toList()
    }

    fun synonymsForWord(word: String): Iterable<String> {
        return thesaurusRepository.getActionsThesaurus().synonymsForWord(word).sorted()
    }

    fun hasWordDetails(word: String): Boolean {
        return thesaurusAPI.hasWordDetailsForVerb(word)
    }

    // TODO: this entire section badly needs tests

    /**
     * Determine if visiting this word would result you learning new Synonyms
     * TODO: Currently this is only one level of depth. Consider traversing the entire graph though.
     * @param word the word you're on
     * @param newWord the word you're considering showing
     */
    fun hasUniqueSynonymsFrom(word: String, newWord: String): Boolean {
        val currentSynonyms = thesaurusRepository.getActionsThesaurus().synonymsForWord(word)
        val newSynonyms = thesaurusRepository.getActionsThesaurus().synonymsForWord(newWord)
        val nonInterestingNewSynomums = newSynonyms - currentSynonyms - word
        return nonInterestingNewSynomums.isNotEmpty()
    }
}
