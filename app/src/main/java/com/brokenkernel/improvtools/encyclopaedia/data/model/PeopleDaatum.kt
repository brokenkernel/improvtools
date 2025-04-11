package com.brokenkernel.improvtools.encyclopaedia.data.model

import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal enum class PeopleDaatum(val personName: String, val knownFor: String) {
    ViolaSpolin("Viola Spolin", "Theater Games"),
    KeithJohnstone("Keith Johnstone", "Impro & Theatresports"),
    PaulSills("Paul Sills", "The Second City"),
    SusanMessing("Susan Messing", " Annoyance Theater and iO Theater"),
    RafeChase("Rafe Chase", "BATS Improv"),
    ArmandoDiaz("Armando Diaz", "The Armando Format"),
    RebeccaStockley ("Rebecca Stockley", "BATS Improv"),
    StephenKerrin("Stephen Kerrin", "BATS Improv"),
    TimOrr("Tim Orr", "BATS Improv"),
    GaryAustin("Gary Austin", "BATS Improv"),
}