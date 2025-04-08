package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import kotlinx.serialization.Serializable

internal interface AudienceSuggestionDatumRepository {
    fun getIdeaCategories(): List<IdeaCategory>
}


@Serializable
internal data class IdeaItem(val idea: String)

@Serializable
internal data class IdeaCategory(
    val title: String,
    val ideas: Set<IdeaItem>,
)

@Serializable
internal data class AudienceSuggestionDatum(
    val categories: List<IdeaCategory>
)
