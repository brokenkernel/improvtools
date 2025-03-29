package com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionCategory
import com.brokenkernel.improvtools.suggestionGenerator.presentation.uistate.SuggestionScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.EnumMap

class SuggestionScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SuggestionScreenUIState())
    internal val uiState: StateFlow<SuggestionScreenUIState> = _uiState.asStateFlow()

    init {
        resetAllCategories()
    }

    internal fun updateSuggestionFor(suggestionCategory: SuggestionCategory): Unit {
        _uiState.value = SuggestionScreenUIState(
            audienceSuggestions = _uiState.value.audienceSuggestions + mapOf(
                suggestionCategory to pickAnotherItemFromCategoryDatum(
                    suggestionCategory,
                    _uiState.value.audienceSuggestions.getValue(suggestionCategory)
                )
            )
        )
    }

    internal fun resetAllCategories(): Unit {
        val audienceSuggestions: MutableMap<SuggestionCategory, String> =
            EnumMap(SuggestionCategory::class.java)

        SuggestionCategory.entries.forEach { item ->
            val word = pickAnotherItemFromCategoryDatum(
                item, _uiState.value.audienceSuggestions.getOrDefault(
                    item,
                    ""
                )
            )
            audienceSuggestions[item] = word
        }
        _uiState.value = SuggestionScreenUIState(audienceSuggestions = audienceSuggestions)

    }

    /**
     * Pick a word that differs from the existing word.
     */
    private fun pickAnotherItemFromCategoryDatum(
        suggestionCategory: SuggestionCategory,
        currentWord: String,
    ): String {
        return (suggestionCategory.ideas - currentWord).random()
    }

}
