package com.brokenkernel.improvtools.encyclopaedia.data.model

// TODO: make things internal once things are modularised

// TODO: see also?

internal val GamesDatum: Collection<GamesDataItem> = listOf(
    GamesDataItem(
        gameName = "Three Things",
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
        unpublishedMatches = setOf("3 Things"),
        tags = setOf(GamesDatumTag.DIVERGENCE),
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
        tags = setOf(GamesDatumTag.MEDIUMFORM),
    ),
    GamesDataItem(
        gameName = "The Harold",
        topic = GamesDatumTopic.FORMAT,
        tags = setOf(GamesDatumTag.MEDIUMFORM),
    ),
    GamesDataItem(
        gameName = "Conducted Story",
        topic = GamesDatumTopic.GAME,
    ),
    GamesDataItem(
        gameName = "Name And Gesture",
        topic = GamesDatumTopic.EXERCISE,
        tags = setOf(GamesDatumTag.NAMES),
    ),
    GamesDataItem(
        gameName = "Character Gauntlet",
        topic = GamesDatumTopic.EXERCISE,
        tags = setOf(GamesDatumTag.RANGE),
        detailedInformation = """
            |A single person is in the center. The other players takes turns endowing the center person with
            |different characters. A brief interaction ensues (typically no more than two lines) and then another person
            |enters with a different character. Repeat for a while and then switch the center person.
        """.trimMargin(),
    ),
    GamesDataItem(
        gameName = "Character Circle",
        topic = GamesDatumTopic.EXERCISE,
        tags = setOf(GamesDatumTag.RANGE),
    ),
    GamesDataItem(
        gameName = "Middle School Warmup",
        topic = GamesDatumTopic.WARMUP,
    ),
    GamesDataItem(
        gameName = "Half Time",
        topic = GamesDatumTopic.GAME,
        tags = setOf(GamesDatumTag.SHORTFORM),
    ),
    GamesDataItem(
        gameName = "Word At a time",
        topic = GamesDatumTopic.GAME,
        tags = setOf(GamesDatumTag.SHORTFORM),
    ),
    GamesDataItem(
        gameName = "Questions Only",
        topic = GamesDatumTopic.GAME,
    ),
    GamesDataItem(
        gameName = "Director's Cut",
        topic = GamesDatumTopic.GAME,
    ),
    GamesDataItem(
        gameName = "Interview Me",
        topic = GamesDatumTopic.EXERCISE,
    ),
    GamesDataItem(
        gameName = "One For All",
        topic = GamesDatumTopic.GAME,
    ),
    GamesDataItem(
        gameName = "Freeze Tag",
        topic = GamesDatumTopic.GAME,
        tags = setOf(GamesDatumTag.SHORTFORM),
    ),
    GamesDataItem(
        gameName = "What are you doing?",
        topic = GamesDatumTopic.GAME,
    ),
    GamesDataItem(
        gameName = "Project To the Hand",
        topic = GamesDatumTopic.EXERCISE,
        tags = setOf(GamesDatumTag.VOCAL),
    ),
    GamesDataItem(
        gameName = "So So Scene",
        topic = GamesDatumTopic.EXERCISE,
    ),
    GamesDataItem(
        gameName = "Name Repetition Opening",
        topic = GamesDatumTopic.EXERCISE,
    ),
    GamesDataItem(
        gameName = "First Three Scenes",
        topic = GamesDatumTopic.EXERCISE,
    ),
    GamesDataItem(
        gameName = "Lines From a Hat",
        topic = GamesDatumTopic.GAME,
        tags = setOf(GamesDatumTag.SHORTFORM),
        detailedInformation = """|
            |The audience is asked for line suggestions. A normal scene ensues.
            |Each actor will occasionally read a line from the suggestions.
            |It should avoided to reference that this line is a quote (e.g., avoid "as my Mom used to say ...")
        """.trimMargin(),
    ),
    GamesDataItem(
        gameName = "Lines From a Phone",
        topic = GamesDatumTopic.GAME,
        tags = setOf(GamesDatumTag.SHORTFORM),
        detailedInformation = """|
            |The audience is asked for a source of text. It may be a text message thread for example.
            |A normal scene ensues.
            |One actor must read ALL of their lines from the source of text.
        """.trimMargin(),
    ),
    GamesDataItem(
        gameName = "Dysfunctional Relationships",
        detailedInformation = """
            | To play the game, two actors come on stage and each draw one relationship card which they look at
            | and show to the audience (but not each other). They then begin a scene where they have to create a
            |  relationship similar to what is on their card, but without negating anything the other actor says.
        """.trimMargin(),
        topic = GamesDatumTopic.GAME,
        source = "https://ask.metafilter.com/233371/I-Need-Some-Relationship-Suggestions",
        tags = setOf(GamesDatumTag.SHORTFORM),
        // TODO: maybe add link to game helper that gives you two relationships?
    ),
    GamesDataItem(
        gameName = "Best of Times, Worst of Times",
        topic = GamesDatumTopic.GAME,
        detailedInformation = """
            |A scene is set up involving some sort of choice.
            |This scene is then replayed twice. The first time every choice
            | works out with the best possible result,
            | in the second, everything ends up going the worst way you can imagine.
        """.trimMargin(),
        tags = setOf(GamesDatumTag.SHORTFORM),
        // TODO: maybe add link to game helper that gives you a scene.
    ),
    GamesDataItem(
        gameName = "Status Swap",
        topic = GamesDatumTopic.GAME,
        tags = setOf(GamesDatumTag.SHORTFORM, GamesDatumTag.STATUS),
    ),
    GamesDataItem(
        gameName = "Deck Of Cards Status",
        topic = GamesDatumTopic.EXERCISE,
        tags = setOf(GamesDatumTag.STATUS),
    ),
    GamesDataItem(
        gameName = "Square Breathing",
        topic = GamesDatumTopic.EXERCISE,
        tags = setOf(GamesDatumTag.BREATH),
    ),
    GamesDataItem(
        gameName = "Get The Stick",
        topic = GamesDatumTopic.EXERCISE,
    ),
    GamesDataItem(
        gameName = "Why are you late? / The boss",
        topic = GamesDatumTopic.EXERCISE,
    ),
)
