package com.brokenkernel.improvtools.encyclopaedia.data.model

// TODO make it easier/simpler to make a new encyclopedia search, segment, list, etc page

internal enum class GlossaryDatumTopic {
    WORD,
}

internal data class GlossaryDataItem(
    val term: String,
    val topic: GlossaryDatumTopic,
//    val knownFor: String,
    val detailedInformation: String = "",
)

internal val GlossaryDatum: Collection<GlossaryDataItem> = listOf(
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
)
