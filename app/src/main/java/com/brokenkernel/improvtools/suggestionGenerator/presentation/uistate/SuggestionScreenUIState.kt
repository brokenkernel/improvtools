package com.brokenkernel.improvtools.suggestionGenerator.presentation.uistate

import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionCategory

data class SuggestionScreenUIState(
    val audienceSuggestions: Map<SuggestionCategory, String> = HashMap(),
    val currentScrambledWord: String = "",
)