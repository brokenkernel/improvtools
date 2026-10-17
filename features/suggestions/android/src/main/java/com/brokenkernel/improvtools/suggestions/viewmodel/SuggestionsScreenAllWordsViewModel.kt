package com.brokenkernel.improvtools.suggestions.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.suggestions.repository.MergedAudienceSuggestionDatumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.collections.immutable.ImmutableList

@HiltViewModel
internal class SuggestionsScreenAllWordsViewModel @Inject constructor(
    suggestionDatumRepository: MergedAudienceSuggestionDatumRepository,
) :
    ViewModel() {

    private val allWordsForCategory: Map<String, ImmutableList<String>> = suggestionDatumRepository
        .getIdeaCategories().associate { it.categoryTitle() to it.allWordsforCategory() }

    internal fun getIdeaForCategory(category: String): List<String> {
        return allWordsForCategory.getOrDefault(category, emptyList())
    }
}
