package com.brokenkernel.improvtools.suggestions.data.storage

import kotlinx.serialization.Serializable

// TODO: ODS should be private/internal and only exposed to the rest via UI structures
// TODO: remove public from most of this. Only exposed while migrating to new packages

@Serializable
public class IdeaItemODS(
    public val idea: String,
    public val explanation: String? = null,
)

@Serializable
public class IdeaCategoryODS(
    private val title: String,
    // perhaps there is a better or more generic way, but this is better than checking the title.
    public val showLinkToEmotion: Boolean = false,
    public val ideas: Set<IdeaItemODS>,
) {
    public fun itemKey(): String {
        return this.title
    }

    public fun titleWithCount(): String {
        return "${this.title} (${ideas.size})"
    }
}

@Serializable
public data class AudienceSuggestionDatumODS(
    val categories: List<IdeaCategoryODS>,
)

public class IdeaUIState private constructor(public val idea: String, public val explanation: String?) {
    public companion object {
        // the modeling and structure is weird and likely needs to be redone. Leave for now.
        public fun fromStoredModel(storedIdea: IdeaItemODS): IdeaUIState {
            return IdeaUIState(storedIdea.idea, storedIdea.explanation)
        }
    }
}
