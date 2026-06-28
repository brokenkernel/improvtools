package com.brokenkernel.improvtools.encyclopaedia.data

import java.net.URI

// TODO: localise wikipedia link
// TODO: figure out how to open a specific item at start, or deep link, or similar. For example filter to 'BATS' or some such
// TODO: make things internal again possible

public enum class PeopleDatumTopic {
    PERSON,
    TROOP,
}

public data class PeopleDataItem(
    val personName: String,
    val topic: PeopleDatumTopic,
    val knownFor: String,
    val learnMoreLink: URI? = null,
    val detailedInformation: String? = null,
) {
    init {
        // sanity check/validation
        if (this.learnMoreLink != null) {
            require(this.learnMoreLink.scheme == "https", { "require secure links for PeopleDataItem" })
        }
    }

    val isWikipedia: Boolean =
        this.learnMoreLink?.host == "en.wikipedia.org"
}

private fun String.toUri(): URI {
    return URI.create(this)
}

public val PeopleDatum: Collection<PeopleDataItem> = listOf(
    PeopleDataItem(
        "Viola Spolin",
        PeopleDatumTopic.PERSON,
        "Theater Games",
        "https://en.wikipedia.org/wiki/Viola_Spolin".toUri(),
        detailedInformation = """
            |Focussed on getting in touch with one's impulses here and now—and acting
            |on them quickly without overthinking or doubting.
        """.trimMargin(),
    ),
    PeopleDataItem(
        "Keith Johnstone",
        PeopleDatumTopic.PERSON,
        "Impro & Theatresports",
        "https://en.wikipedia.org/wiki/Keith_Johnstone".toUri(),
    ),
    PeopleDataItem(
        "Paul Sills",
        PeopleDatumTopic.PERSON,
        "The Second City",
        "https://en.wikipedia.org/wiki/Paul_Sills".toUri(),
    ),
    PeopleDataItem(
        "Susan Messing",
        PeopleDatumTopic.PERSON,
        "Annoyance Theater and iO Theater",
        learnMoreLink = "https://en.wikipedia.org/wiki/Susan_Messing".toUri(),
    ),
    PeopleDataItem(
        "Rafe Chase",
        PeopleDatumTopic.PERSON,
        "BATS Improv",
    ),
    PeopleDataItem(
        "Armando Diaz",
        PeopleDatumTopic.PERSON,
        "Magnet Theatre",
        learnMoreLink = "https://en.wikipedia.org/wiki/Armando_Diaz".toUri(),
    ),
    PeopleDataItem(
        "Rebecca Stockley",
        PeopleDatumTopic.PERSON,
        "BATS Improv"
    ),
    PeopleDataItem(
        "Stephen Kerrin",
        PeopleDatumTopic.PERSON,
        "BATS Improv"
    ),
    PeopleDataItem(
        "Tim Orr",
        PeopleDatumTopic.PERSON,
        "BATS Improv"
    ),
    PeopleDataItem(
        "Gary Austin",
        PeopleDatumTopic.PERSON,
        "BATS Improv"
    ),
    PeopleDataItem(
        "Konstantin Stanislavski",
        PeopleDatumTopic.PERSON,
        "",
        learnMoreLink = "https://en.wikipedia.org/wiki/Konstantin_Stanislavski".toUri(),
    ),
    PeopleDataItem(
        personName = "Sanford Meisner",
        topic = PeopleDatumTopic.PERSON,
        knownFor = "Meisner Technique",
        learnMoreLink = "https://en.wikipedia.org/wiki/Sanford_Meisner".toUri(),
    ),
    PeopleDataItem(
        "BATS Improv",
        PeopleDatumTopic.TROOP,
        "",
        "https://en.wikipedia.org/wiki/BATS_Improv".toUri(),
    ),
    PeopleDataItem(
        "Commedia Dell'arte",
        PeopleDatumTopic.TROOP,
        "",
        "https://en.wikipedia.org/wiki/Commedia_dell'arte".toUri(),
    ),
    PeopleDataItem(
        "Magnet Theater",
        PeopleDatumTopic.TROOP,
        "",
        "https://en.wikipedia.org/wiki/Magnet_Theater".toUri(),
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
    PeopleDataItem(
        "David Shepherd",
        PeopleDatumTopic.PERSON,
        "The Compass Players"
    ),
    PeopleDataItem(
        "Elaine May",
        PeopleDatumTopic.PERSON,
        "comedic improv",
        learnMoreLink = "https://en.wikipedia.org/wiki/Elaine_May".toUri(),
    ),
    PeopleDataItem(
        "Alan Myerson",
        PeopleDatumTopic.PERSON,
        "The Committee",
        learnMoreLink = "https://en.wikipedia.org/wiki/Alan_Myerson".toUri(),
    ),
    PeopleDataItem(
        "Augusto Boal",
        PeopleDatumTopic.PERSON,
        "Theatre of the Oppressed",
        learnMoreLink = "https://en.wikipedia.org/wiki/Augusto_Boal".toUri(),
    ),
    PeopleDataItem(
        personName = "https://en.wikipedia.org/wiki/Theatre_of_the_Oppressed",
        topic = PeopleDatumTopic.TROOP,
        knownFor = "",
        learnMoreLink = "https://en.wikipedia.org/wiki/Theatre_of_the_Oppressed".toUri(),
    ),
    PeopleDataItem(
        "Ruth Zaporah",
        PeopleDatumTopic.PERSON,
        "Action Theatre",
        learnMoreLink = "https://en.wikipedia.org/wiki/Action_Theatre_(Ruth_Zaporah)".toUri(),
    ),
    PeopleDataItem(
        "William Hall",
        PeopleDatumTopic.PERSON,
        "BATS Improv",
        learnMoreLink = "https://en.wikipedia.org/wiki/William_Hall_(actor)".toUri(),
    ),
    PeopleDataItem(
        "Brave New Workshop",
        PeopleDatumTopic.TROOP,
        "Oldest US comedy theater",
        learnMoreLink = "https://en.wikipedia.org/wiki/Brave_New_Workshop".toUri(),
    ),
)
