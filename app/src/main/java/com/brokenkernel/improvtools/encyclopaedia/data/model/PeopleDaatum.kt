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

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal enum class PeopleDaatum(
    val personName: String,
    val topic: PeopleDatumTopic,
    val knownFor: String,
    val wikipediaLink: Uri? = null,
    val detailedInformation: String = "",
) {
    ViolaSpolin(
        "Viola Spolin",
        PeopleDatumTopic.PERSON,
        "Theater Games",
        "https://en.wikipedia.org/wiki/Viola_Spolin".toUri(),
        detailedInformation = """
            |Focussed on getting in touch with one's impulses here and now—and acting
            |on them quickly without overthinking or doubting.""".trimMargin()
    ),
    KeithJohnstone(
        "Keith Johnstone",
        PeopleDatumTopic.PERSON,
        "Impro & Theatresports",
        "https://en.m.wikipedia.org/wiki/Keith_Johnstone".toUri(),
    ),
    PaulSills(
        "Paul Sills",
        PeopleDatumTopic.PERSON,
        "The Second City",
        "https://en.wikipedia.org/wiki/Paul_Sills".toUri(),
    ),
    SusanMessing("Susan Messing", PeopleDatumTopic.PERSON, "Annoyance Theater and iO Theater"),
    RafeChase("Rafe Chase", PeopleDatumTopic.PERSON, "BATS Improv"),
    ArmandoDiaz("Armando Diaz", PeopleDatumTopic.PERSON, "Magnet Theatre"),
    RebeccaStockley("Rebecca Stockley", PeopleDatumTopic.PERSON, "BATS Improv"),
    StephenKerrin("Stephen Kerrin", PeopleDatumTopic.PERSON, "BATS Improv"),
    TimOrr("Tim Orr", PeopleDatumTopic.PERSON, "BATS Improv"),
    GaryAustin("Gary Austin", PeopleDatumTopic.PERSON, "BATS Improv"),
    KonstantinStanislavski("Konstantin Stanislavski", PeopleDatumTopic.PERSON, "¸"),

    // todo: figure out how to combine both people and troops?
    // todo: add segment button for people and troops
    CommediaDellArte("commedia dell'arte", PeopleDatumTopic.TROOP, "", "https://en.wikipedia.org/wiki/Commedia_dell'arte".toUri(),),
    MagnetTheater("Magnet Theater", PeopleDatumTopic.TROOP, "", "https://en.wikipedia.org/wiki/Magnet_Theater".toUri(),),

    JacquesCopeau(
        "Jacques Copeau",
        PeopleDatumTopic.PERSON,
        "",
        "https://en.wikipedia.org/wiki/Jacques_Copeau".toUri(),
    ),
    DudleyRiggs(
        "Dudley Riggs",
        PeopleDatumTopic.PERSON,
        "Audience Suggestions",
        "https://en.wikipedia.org/wiki/Dudley_Riggs".toUri(),
    ),
    CliveBarker(
        "Clive Barker",
        PeopleDatumTopic.PERSON,
        "Theatre Games",
        "https://en.wikipedia.org/wiki/Clive_Barker_(editor)".toUri(),
    ),
    DavidShepherd("David Shepherd", PeopleDatumTopic.PERSON, "The Compass Players"),
    ElaineMay("Elaine May", PeopleDatumTopic.PERSON, "comedic improv"),
    AlanMyerson("Alan Myerson", PeopleDatumTopic.PERSON, "The Committee"),
    AugustoBoal("Augusto Boal", PeopleDatumTopic.PERSON, "Theatre of the Oppressed"),
    RuthZaporah("Ruth Zaporah", PeopleDatumTopic.PERSON, "Action Theatre"),
    WilliamHall("William Hall", PeopleDatumTopic.PERSON, "BATS Improv"),
    ;

    init {
        // sanity check/validation
        if (this.wikipediaLink != null) {
            require(this.wikipediaLink.scheme == "https")
        }
    }

}