package com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.encyclopaedia.data.model.ActionsThesaurus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThesaurusTabAllItemsViewModel @Inject constructor() : ViewModel() {
    fun words(): Iterable<String> {
        return ActionsThesaurus.keys()
    }

    fun synonymsForWord(word: String): Iterable<String> {
        return ActionsThesaurus.synonymsForWord(word).sorted()
    }

    // TODO: this entire section badly needs tests

    /**
     * Determine if visiting this word would result you learning new Synonyms
     * TODO: Currently this is only one level of depth. Consider traversing the entire graph though.
     * @param word the word you're on
     * @param newWord the word you're considering showing
     */
    fun hasUniqueSynonymsFrom(word: String, newWord: String): Boolean {
        val currentSynonyms = ActionsThesaurus.synonymsForWord(word)
        val newSynonyms = ActionsThesaurus.synonymsForWord(newWord)
        val nonInterestingNewSynomums = newSynonyms - currentSynonyms
        return nonInterestingNewSynomums.isNotEmpty()
    }
}
