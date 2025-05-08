package com.brokenkernel.improvtools.encyclopaedia.data.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class GlossaryDataItem(
    val term: String,
    val detailedInformation: String,
)

internal val GlossaryDatum: ImmutableList<GlossaryDataItem> = persistentListOf(
    GlossaryDataItem(
        term = "Blocking",
        detailedInformation = """
            |Not accepting an offer. This can even be your own offer.
        """.trimMargin(),
    ),
    GlossaryDataItem(
        term = "Offer",
        detailedInformation = """
            |Any action that may tell more about the world or story.
        """.trimMargin(),
    ),
    GlossaryDataItem(
        term = "Callback",
        detailedInformation = """
            |Revisiting characters, scenes, or situations from prior scenes.
        """.trimMargin(),
    ),
    GlossaryDataItem(
        term = "Grounded",
        detailedInformation = """
            |Playing close to truthful. The believable scene.
        """.trimMargin(),
    ),
    GlossaryDataItem(
        term = "Edit",
        detailedInformation = """
            | Ending a scene.
        """.trimMargin(),
    ),
    GlossaryDataItem(
        term = "Pimping",
        detailedInformation = """
            | Getting another player to do something specific. For example to tell a poem or sing a song.
        """.trimMargin(),
    ),
    GlossaryDataItem(
        term = "Platform",
        detailedInformation = """
            | The basics of a scene or story. The 'typical' or 'day to day' of a scene. See CROWE.
        """.trimMargin(),
    ),
)
