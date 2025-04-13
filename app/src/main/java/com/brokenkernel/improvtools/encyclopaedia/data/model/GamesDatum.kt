package com.brokenkernel.improvtools.encyclopaedia.data.model

import androidx.annotation.RestrictTo

/**
 * @param unpublishedMatches This is a set of strings which will match but are not shown to the user. Useful for "3" -> "Three" or similar
 */

internal enum class GamesDatumTopic {
    GAME,
    WARMUP,
    FORMAT,
}
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal enum class GamesDatum(
    val gameName: String,
    val topic: GamesDatumTopic,
    val detailedInformation: String = "",
    val unpublishedMatches: Set<String> = emptySet<String>(),
) {
    ThreeThings(
        gameName = "3 Things",
        topic = GamesDatumTopic.GAME,
        detailedInformation = """ """,
        unpublishedMatches = setOf("Three Things"),
    ),
    Ball(
        gameName = "Ball",
        topic = GamesDatumTopic.WARMUP,
    ),
    Armando(
        gameName = "The Armando",
        topic = GamesDatumTopic.FORMAT,
        detailedInformation = """
            |A Harlold based a monolog.
            |Typically this monolog is inspired by a suggestion itself.
        """.trimMargin(),
    ),
    Harold(
        gameName = "The Harold",
        topic = GamesDatumTopic.FORMAT,
    ),
    BestOfTimesWorstOfTimes(
        gameName = "Best of Times, Worst of Times",
        topic = GamesDatumTopic.GAME,
        detailedInformation = """
            |A scene is set up involving some sort of choice.
            |This scene is then replayed twice. The first time every choice
            | works out with the best possible result,
            | in the second, everything ends up going the worst way you can imagine.
        """.trimMargin().replace("\n", "")),
}