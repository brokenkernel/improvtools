package com.brokenkernel.improvtools.encyclopaedia.data.model

/**
 * @param unpublishedMatches Strings which will match but are not shown to the user Useful for "3" -> "Three" or similar
 */
internal data class GamesDataItem(
    val gameName: String,
    val topic: GamesDatumTopic,
    val detailedInformation: String? = null,
    val unpublishedMatches: Set<String> = emptySet<String>(),
    val source: String? = null,
    val tags: Set<GamesDatumTag> = setOf(),
)
