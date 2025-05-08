package com.brokenkernel.improvtools.suggestionGenerator.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class IdeaItemODS(val idea: String, val explanation: String? = null)

@Serializable
internal data class IdeaCategoryODS(
    private val title: String,
    // perhaps there is a better or more generic way, but this is better than checking the title.
    val showLinkToEmotion: Boolean = false,
    val ideas: Set<IdeaItemODS>,
) {
    internal fun itemKey(): String {
        return this.title
    }

    internal fun titleWithCount(): String {
        return "${this.title} (${ideas.size})"
    }
}

@Serializable
internal data class AudienceSuggestionDatumODS(
    val categories: List<IdeaCategoryODS>,
)

internal data class IdeaUIState(val idea: String, val explanation: String?) {
    companion object {
        // the modeling and structure is weird and likely needs to be redone. Leave for now.
        fun fromStoredModel(storedIdea: IdeaItemODS): IdeaUIState {
            return IdeaUIState(storedIdea.idea, storedIdea.explanation)
        }
    }
}
