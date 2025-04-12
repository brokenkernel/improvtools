package com.brokenkernel.improvtools.encyclopaedia.data.model

import androidx.annotation.RestrictTo

/**
 * @param unpublishedMatches This is a set of strings which will match but are not shown to the user. Useful for "3" -> "Three" or similar
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal enum class GamesDatum(
    val gameName: String,
    val topic: String,
    val detailedInformation: String = "",
    val unpublishedMatches: Set<String> = emptySet<String>(),
) {
    ThreeThings(
        gameName = "3 Things",
        topic = "Game",
        detailedInformation = """ """,
        unpublishedMatches = setOf("Three Things"),
    ),
    Ball(
        gameName = "Ball",
        topic = "Warmup",
    ),
    Armando(
        gameName = "The Armando",
        topic = "Format",
        detailedInformation = """
            |A Harlold based a monolog.
            |Typically this monolog is inspired by a suggestion itself.
        """.trimMargin(),
    ),
    Harold(
        gameName = "The Harold",
        topic = "Format",
    ),
}