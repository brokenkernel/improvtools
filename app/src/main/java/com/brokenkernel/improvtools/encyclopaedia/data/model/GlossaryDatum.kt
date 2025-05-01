package com.brokenkernel.improvtools.encyclopaedia.data.model

internal enum class GlossaryDatumTopic {
    WORD,
}

internal data class GlossaryDataItem(
    val term: String,
    val topic: GlossaryDatumTopic,
    val detailedInformation: String,
)

internal val GlossaryDatum: Collection<GlossaryDataItem> = listOf(
    GlossaryDataItem(
        term = "Blocking",
        topic = GlossaryDatumTopic.WORD,
        detailedInformation = """
            |Not accepting an offer. This can even be your own offer.
        """.trimMargin(),
    ),
    GlossaryDataItem(
        term = "Offer",
        topic = GlossaryDatumTopic.WORD,
        detailedInformation = """
            |Any action that may tell more about the world or story.
        """.trimMargin(),
    ),
    GlossaryDataItem(
        term = "Callback",
        topic = GlossaryDatumTopic.WORD,
        detailedInformation = """
            |Revisiting characters, scenes, or situations from prior scenes.
        """.trimMargin(),
    ),
    GlossaryDataItem(
        term = "Grounded",
        topic = GlossaryDatumTopic.WORD,
        detailedInformation = """
            |Playing close to truthful. The believable scene.
        """.trimMargin(),
    ),
    GlossaryDataItem(
        term = "Edit",
        topic = GlossaryDatumTopic.WORD,
        detailedInformation = """
            | Ending a scene.
        """.trimMargin(),
    ),
    GlossaryDataItem(
        term = "Pimping",
        topic = GlossaryDatumTopic.WORD,
        detailedInformation = """
            | Getting another player to do something specific. For example to tell a poem or sing a song.
        """.trimMargin(),
    ),
    GlossaryDataItem(
        term = "Platform",
        topic = GlossaryDatumTopic.WORD,
        detailedInformation = """
            | The basics of a scene or story. The 'typical' or 'day to day' of a scene. See CROWE.
        """.trimMargin(),
    ),
)
