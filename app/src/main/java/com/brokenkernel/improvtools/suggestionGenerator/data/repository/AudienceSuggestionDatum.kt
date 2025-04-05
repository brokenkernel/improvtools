package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionCategory
import kotlinx.serialization.Serializable

internal interface AudienceSuggestionDatumRepository {
    fun getAudienceDatumForCategory(category: SuggestionCategory): Set<String>
}

@Serializable
internal data class AudienceSuggestionDatum(
    val categories: Map<String, Set<String>>,
)

