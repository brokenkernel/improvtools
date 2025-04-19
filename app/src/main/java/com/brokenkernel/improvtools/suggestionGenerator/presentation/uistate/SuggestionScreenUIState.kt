package com.brokenkernel.improvtools.suggestionGenerator.presentation.uistate

internal data class SuggestionScreenUIState(
    val shouldReuseSuggestions: Boolean,
) {
    companion object {
        fun default(): SuggestionScreenUIState {
            return SuggestionScreenUIState(shouldReuseSuggestions = false)
        }
    }
}
