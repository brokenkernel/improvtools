package com.brokenkernel.improvtools.encyclopaedia.data.model

import android.net.Uri
import androidx.annotation.RestrictTo
import androidx.core.net.toUri

// TODO: add link to wikipedia for others
// TODO: use link to wikipedia

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal enum class PeopleDaatum(val personName: String, val knownFor: String, val wikipediaLink: Uri? = null) {
    ViolaSpolin("Viola Spolin", "Theater Games", "https://en.wikipedia.org/wiki/Viola_Spolin".toUri()),
    KeithJohnstone("Keith Johnstone", "Impro & Theatresports"),
    PaulSills("Paul Sills", "The Second City"),
    SusanMessing("Susan Messing", " Annoyance Theater and iO Theater"),
    RafeChase("Rafe Chase", "BATS Improv"),
    ArmandoDiaz("Armando Diaz", "The Armando Format"),
    RebeccaStockley ("Rebecca Stockley", "BATS Improv"),
    StephenKerrin("Stephen Kerrin", "BATS Improv"),
    TimOrr("Tim Orr", "BATS Improv"),
    GaryAustin("Gary Austin", "BATS Improv"),
    KonstantinStanislavski("Konstantin Stanislavski", "acting"),
    CommediaDellArte("commedia dell'arte", ""),
    JacquesCopeau("Jacques Copeau", "Jacques Copeau"),
    DudleyRiggs("Dudley Riggs", "Audience Suggestions"),
    CliveBarker("Clive Barker", "Theatre Games"),
    DavidShepherd("David Shepherd", "The Compass Players"),
    ElaineMay("Elaine May", "comedic improv"),
    AlanMyerson("Alan Myerson", "The Committee"),
    AugustoBoal("Augusto Boal", "Theatre of the Oppressed"),
    RuthZaporah("Ruth Zaporah", "Action Theatre"),
    WilliamHall("William Hall", "BATS Improv"),
    ;

    init {
        // sanity check/validation
        if (this.wikipediaLink != null) {
            require(this.wikipediaLink.scheme == "https")
        }
    }

}