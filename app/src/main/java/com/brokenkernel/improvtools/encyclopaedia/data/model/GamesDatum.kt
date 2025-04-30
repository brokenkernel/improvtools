package com.brokenkernel.improvtools.encyclopaedia.data.model

internal enum class GamesDatumTopic {
    GAME,
    WARMUP,
    EXERCISE,
    FORMAT,
    ICEBREAKER,
}

/**
 * @param unpublishedMatches This is a set of strings which will match but are not shown to the user Useful for "3" -> "Three" or similar
 */
internal data class GamesDataItem(
    val gameName: String,
    val topic: GamesDatumTopic,
    val detailedInformation: String = "",
    val unpublishedMatches: Set<String> = emptySet<String>(),
)

internal val GamesDatum: Collection<GamesDataItem> = listOf(
    GamesDataItem(
        gameName = "3 Things",
        topic = GamesDatumTopic.GAME,
        detailedInformation = """
             The players stand in a circle. The first person turns to the next and
             says <q>three things</q> and the next person should say three things
             which match the category.
             Critically it should be:
             <ul>
             <li>As fast as possible (high energy).  Avoid filler words like "erm" or "uh"
             <li>Answers don't need to be 'correct' or 'true' (they automatically are!)
             <li>Treated as if the questions and answers are the most important ever given.
             </ul>
             <b>Variations:</b> 15 Things, Category Things (name the category), Word-At-A-Time things,
        """.trimIndent(),
        unpublishedMatches = setOf("Three Things"),
    ),
    // TODO: how to handle linking to variations? More details?
    //  TODO variations: 15 things ?  2.5 things? Other
    GamesDataItem(
        gameName = "Ball",
        topic = GamesDatumTopic.WARMUP,
        detailedInformation = """
            |Stand in a circle. Players take turns hitting a ball.
            |The game ends if:
            |<ul>
            |<li>Any player hits the ball more than once in a row.</li>
            |<li>The ball hits the floor.</li>
            |</ul>
        """.trimMargin(),
    ),
    GamesDataItem(
        gameName = "The Armando",
        topic = GamesDatumTopic.FORMAT,
        detailedInformation = """
            |A Harlold based a monolog.
            |Typically this monolog is inspired by a suggestion itself.
        """.trimMargin(),
    ),
    GamesDataItem(
        gameName = "The Harold",
        topic = GamesDatumTopic.FORMAT,
    ),
    GamesDataItem(
        gameName = "Conducted Story",
        topic = GamesDatumTopic.GAME,
    ),
    GamesDataItem(
        gameName = "Name And Gesture",
        topic = GamesDatumTopic.ICEBREAKER,
    ),
    GamesDataItem(
        gameName = "Character Gauntlet",
        topic = GamesDatumTopic.EXERCISE,
    ),
    GamesDataItem(
        gameName = "Character Circle",
        topic = GamesDatumTopic.EXERCISE,
    ),
    GamesDataItem(
        gameName = "Middle School Warmup",
        topic = GamesDatumTopic.WARMUP,
    ),
    GamesDataItem(
        gameName = "Half Time",
        topic = GamesDatumTopic.GAME,
    ),
    GamesDataItem(
        gameName = "Word At a time",
        topic = GamesDatumTopic.GAME,
    ),
    GamesDataItem(
        gameName = "Questions Only",
        topic = GamesDatumTopic.GAME,
    ),
    GamesDataItem(
        gameName = "Genre Replay",
        topic = GamesDatumTopic.GAME,
    ),
    GamesDataItem(
        gameName = "One For All",
        topic = GamesDatumTopic.GAME,
    ),
    GamesDataItem(
        gameName = "Best of Times, Worst of Times",
        topic = GamesDatumTopic.GAME,
        detailedInformation = """
            |A scene is set up involving some sort of choice.
            |This scene is then replayed twice. The first time every choice
            | works out with the best possible result,
            | in the second, everything ends up going the worst way you can imagine.
        """.trimMargin().replace("\n", ""),
    ),
)
