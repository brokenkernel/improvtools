package com.brokenkernel.improvtools.suggestionGenerator.data.model

import android.icu.text.Collator
import android.icu.util.ULocale
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
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
