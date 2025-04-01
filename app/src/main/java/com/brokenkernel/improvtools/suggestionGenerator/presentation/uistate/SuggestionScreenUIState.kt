package com.brokenkernel.improvtools.suggestionGenerator.presentation.uistate

import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionCategory
import java.util.EnumMap

internal data class SuggestionScreenUIState(
    val audienceSuggestions: Map<SuggestionCategory, String> = EnumMap(SuggestionCategory::class.java),
    val shouldReuseSuggestions: Boolean
) {
    companion object {
        fun default(): SuggestionScreenUIState {
            return SuggestionScreenUIState(shouldReuseSuggestions=false)
        }
    }
}