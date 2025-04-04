package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionCategory

internal interface AudienceSuggestionDatumRepository {
    fun getAudienceDatumForCategory(category: SuggestionCategory): Set<String>
}

internal data class AudienceSuggestionDatum(
    val categories: Map<String, Set<String>>,
)

