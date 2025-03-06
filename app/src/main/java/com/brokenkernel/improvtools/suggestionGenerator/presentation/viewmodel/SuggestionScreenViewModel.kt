package com.brokenkernel.improvtools.suggestionGenerator.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionCategory
import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionDatum
import kotlinx.coroutines.flow.MutableStateFlow

class SuggestionsActivityViewModel : ViewModel() {

    val audienceSuggestions: MutableMap<SuggestionCategory, String> = HashMap()
    val audienceSuggestionsAsState = MutableStateFlow(this.audienceSuggestions)

    fun updateSuggestionFor(suggestionCategory: SuggestionCategory) {
        audienceSuggestions.put(suggestionCategory, suggestionCategory.ideas.random())
    }

    fun initAllSuggestions() {
        // TODO move this into ctor or similar?
        SuggestionDatum.allCategories.forEach { item ->
            audienceSuggestions.put(item, item.ideas.random())
        }
    }
}