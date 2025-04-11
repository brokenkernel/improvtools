package com.brokenkernel.improvtools.encyclopaedia.data.model

import androidx.annotation.RestrictTo

/**
 * @param unpublishedMatches This is a set of strings which will match but are not shown to the user. Useful for "3" -> "Three" or similar
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal enum class GamesDatum(
    val gameName: String,
    val topic: String,
    val unpublishedMatches: Set<String> = emptySet<String>(),
) {
    ThreeThings("3 Things", "Game", setOf("Three Things")),
    Ball("Ball", "Warmup"),
    Armando("The Armando", "Format"),
    Harold("The Harold", "Format"),
}