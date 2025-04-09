package com.brokenkernel.improvtools.suggestionGenerator.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class IdeaItem(val idea: String)

@Serializable
internal data class IdeaCategory(
    val title: String,
    val ideas: Set<IdeaItem>,
) {
    internal fun titleWithCount(): String {
        return "${this.title} (${ideas.size})"

    }
}

@Serializable
internal data class AudienceSuggestionDatum(
    val categories: List<IdeaCategory>
)
