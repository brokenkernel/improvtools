package com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionCategory
import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionDatum
import com.brokenkernel.improvtools.suggestionGenerator.presentation.uistate.SuggestionScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SuggestionScreenViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(SuggestionScreenUIState())
    val uiState: StateFlow<SuggestionScreenUIState> = _uiState.asStateFlow()

    init {
        val audienceSuggestions: MutableMap<SuggestionCategory, String> = HashMap()

        SuggestionDatum.allCategories.forEach { item ->
            audienceSuggestions[item] = item.ideas.random()
        }
        _uiState.value = SuggestionScreenUIState(audienceSuggestions = audienceSuggestions)

    }

    //     fun anotherIdea() {
    //        ideas -
    //    }

    fun updateSuggestionFor(suggestionCategory: SuggestionCategory) {
        _uiState.value = SuggestionScreenUIState(
            audienceSuggestions = _uiState.value.audienceSuggestions + mapOf(suggestionCategory to suggestionCategory.ideas.random())
        )
    }

}
