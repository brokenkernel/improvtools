package com.brokenkernel.improvtools.suggestionGenerator.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class IdeaItem(val idea: String)

@Serializable
internal data class IdeaCategory(
    private val title: String,
    // perhaps there is a better or more generic way, but this is better than checking the title.
    val showLinkToEmotion: Boolean = false,
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
