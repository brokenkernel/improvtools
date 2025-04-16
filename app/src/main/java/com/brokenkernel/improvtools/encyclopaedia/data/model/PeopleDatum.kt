package com.brokenkernel.improvtools.encyclopaedia.data.model

import android.net.Uri
import androidx.annotation.RestrictTo
import androidx.core.net.toUri

// TODO: add link to wikipedia for others
// TODO: localise wikipedia link

internal enum class PeopleDatumTopic {
    PERSON,
    TROOP,
}


internal data class PeopleDataItem(
    val personName: String,
    val topic: PeopleDatumTopic,
    val knownFor: String,
    val wikipediaLink: Uri? = null,
    val detailedInformation: String = "",
) {
    init {
        // sanity check/validation
        if (this.wikipediaLink != null) {
            require(this.wikipediaLink.scheme == "https")
        }
    }
}

internal val PeopleDatum: Collection<PeopleDataItem> = listOf(
    PeopleDataItem(
        "Viola Spolin",
        PeopleDatumTopic.PERSON,
        "Theater Games",
        "https://en.wikipedia.org/wiki/Viola_Spolin".toUri(),
        detailedInformation = """
            |Focussed on getting in touch with one's impulses here and now—and acting
            |on them quickly without overthinking or doubting.""".trimMargin()
    ),
    PeopleDataItem(
        "Keith Johnstone",
        PeopleDatumTopic.PERSON,
        "Impro & Theatresports",
        "https://en.m.wikipedia.org/wiki/Keith_Johnstone".toUri(),
    ),
    PeopleDataItem(
        "Paul Sills",
        PeopleDatumTopic.PERSON,
        "The Second City",
        "https://en.wikipedia.org/wiki/Paul_Sills".toUri(),
    ),
    PeopleDataItem("Susan Messing", PeopleDatumTopic.PERSON, "Annoyance Theater and iO Theater"),
    PeopleDataItem("Rafe Chase", PeopleDatumTopic.PERSON, "BATS Improv"),
    PeopleDataItem("Armando Diaz", PeopleDatumTopic.PERSON, "Magnet Theatre"),
    PeopleDataItem("Rebecca Stockley", PeopleDatumTopic.PERSON, "BATS Improv"),
    PeopleDataItem("Stephen Kerrin", PeopleDatumTopic.PERSON, "BATS Improv"),
    PeopleDataItem("Tim Orr", PeopleDatumTopic.PERSON, "BATS Improv"),
    PeopleDataItem("Gary Austin", PeopleDatumTopic.PERSON, "BATS Improv"),
    PeopleDataItem("Konstantin Stanislavski", PeopleDatumTopic.PERSON, "¸"),

    // todo: figure out how to combine both people and troops?
    // todo: add segment button for people and troops
    PeopleDataItem(
        "commedia dell'arte",
        PeopleDatumTopic.TROOP,
        "",
        "https://en.wikipedia.org/wiki/Commedia_dell'arte".toUri(),
    ),
    PeopleDataItem(
        "Magnet Theater",
        PeopleDatumTopic.TROOP,
        "",
        "https://en.wikipedia.org/wiki/Magnet_Theater".toUri()
    ),

    PeopleDataItem(
        "Jacques Copeau",
        PeopleDatumTopic.PERSON,
        "",
        "https://en.wikipedia.org/wiki/Jacques_Copeau".toUri(),
    ),
    PeopleDataItem(
        "Dudley Riggs",
        PeopleDatumTopic.PERSON,
        "Audience Suggestions",
        "https://en.wikipedia.org/wiki/Dudley_Riggs".toUri(),
    ),
    PeopleDataItem(
        "Clive Barker",
        PeopleDatumTopic.PERSON,
        "Theatre Games",
        "https://en.wikipedia.org/wiki/Clive_Barker_(editor)".toUri(),
    ),
    PeopleDataItem("David Shepherd", PeopleDatumTopic.PERSON, "The Compass Players"),
    PeopleDataItem("Elaine May", PeopleDatumTopic.PERSON, "comedic improv"),
    PeopleDataItem("Alan Myerson", PeopleDatumTopic.PERSON, "The Committee"),
    PeopleDataItem("Augusto Boal", PeopleDatumTopic.PERSON, "Theatre of the Oppressed"),
    PeopleDataItem("Ruth Zaporah", PeopleDatumTopic.PERSON, "Action Theatre"),
    PeopleDataItem("William Hall", PeopleDatumTopic.PERSON, "BATS Improv"),
)