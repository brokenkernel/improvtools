package com.brokenkernel.improvtools.encyclopaedia.android.thesaurus.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.encyclopaedia.data.WordType
import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap

// TODO: internal
@HiltViewModel
public class ThesaurusTabAllItemsViewModel @Inject constructor(
    thesaurusRepository: ThesaurusRepository,
) : ViewModel() {
    private val dictionary = thesaurusRepository.getDictionaryInfo()

    // TODO: internal
    public fun groupedWords(): ImmutableMap<String, List<String>> {
        return dictionary.getWordsByType(WordType.ACTION).sorted().groupBy { k ->
            k[0].uppercase()
        }.toImmutableMap()
    }

    // TODO: internal
    public fun synonymsForWord(word: String): Iterable<String> {
        return dictionary.synonymsForWord(word).sorted()
    }

    // TODO: this entire section badly needs tests

    /**
     * Determine if visiting this word would result you learning new Synonyms
     * TODO: Currently this is only one level of depth. Consider traversing the entire graph though.
     * @param word the word you're on
     * @param newWord the word you're considering showing
     */
    // TODO: internal
    public fun hasUniqueSynonymsFrom(word: String, newWord: String): Boolean {
        val currentSynonyms = dictionary.synonymsForWord(word)
        val newSynonyms = dictionary.synonymsForWord(newWord)
        val nonInterestingNewSynomums = newSynonyms - currentSynonyms - word
        return nonInterestingNewSynomums.isNotEmpty()
    }
}
