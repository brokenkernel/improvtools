package com.brokenkernel.improvtools.tonguetwister.data

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class TongueTwisterItem(
    private val text: String,
    val explanation: String? = null,
) {
    fun asHtmlText(): String {
        return text.replace("\n", "<br>")
    }

    fun asRawText(): String {
        return text
    }
}

internal val TongueTwisterDatum: ImmutableList<TongueTwisterItem> = persistentListOf(
    TongueTwisterItem(
        "Peter Piper picked a peck of pickled peppers A peck of pickled peppers Peter Piper picked If Peter Piper picked a peck of pickled peppers Where’s the peck of pickled peppers Peter Piper picked?",
    ),
    TongueTwisterItem(
        "How much wood would a woodchuck chuck if a woodchuck could chuck wood? He would chuck, he would, as much as he could, and chuck as much wood as a woodchuck would if a woodchuck could chuck wood.",
    ),
    TongueTwisterItem(
        """Which wristwatch is a Swiss wristwatch?
            Which wristwatch is an Irish wristwatch?"""
            .trimMargin(),
    ),
    TongueTwisterItem("How can a clam cram in a clean cream can?"),
    TongueTwisterItem("The thirty-three thieves thought that they thrilled the throne throughout Thursday."),
    TongueTwisterItem(
        """As one black bug, bled blue, black blood.
        The other black bug bled blue."""
            .trimMargin(),
    ),
    TongueTwisterItem("You know New York, you need New York. You know you need unique New York."),
    TongueTwisterItem("The bloke on the bike's back brake block broke."),
    TongueTwisterItem(
        "Fuzzy Wuzzy was a bear. A Fuzzy bear was Wuzzy. Fuzzy Wuzzy had no hair. Fuzzy Wuzzy wasn’t very fuzzy, was he?",
    ),
    TongueTwisterItem(
        "If you must cross a course cross cow across a crowded cow crossing, cross the cross coarse cow across the crowded cow crossing carefully.",
    ),
    TongueTwisterItem(
        """Betty bought a bit of butter.
           But the bit of butter Betty bought was bitter.
           If I put it in my batter, it will make my batter bitter
           But a bit of better butter will make my batter better
           So, Betty bought a better bit of butter.
        """.trimMargin(),
    ),
    TongueTwisterItem(
        """To sit in solemn silence in a dull, dark dock,
            In a pestilential prison, with a life-long lock,
            Awaiting the sensation of a short, sharp shock,
            From a cheap and chippy chopper on a big black block!
        """.trimMargin(),
    ),
    TongueTwisterItem("""If a dog chews shoes, whose shoes does he choose?"""),
    TongueTwisterItem("""No need to light a night-light on a light night like tonight."""),
    TongueTwisterItem("""Six sick hicks nick six slick bricks with picks and sticks."""),
    TongueTwisterItem(
        """Brisk brave brigadiers brandished broad bright blades, blunderbusses, and bludgeons—balancing them badly.""",
    ),
    TongueTwisterItem("""The sixth sick sheik's sixth sheep's sick."""),
    TongueTwisterItem(
        """If practice makes perfect and perfect needs practice, I’m perfectly practised and practically perfect.""",
    ),
    TongueTwisterItem(
        """If you must cross a course cross cow across a crowded cow crossing, cross the cross coarse cow across the crowded cow crossing carefully.""",
    ),
    TongueTwisterItem(
        """I thought a thought. But the thought I thought wasn’t the thought I thought I thought. If the thought I thought I thought had been the thought I thought, I wouldn’t have thought I thought.""",
    ),
    TongueTwisterItem("""A proper cup of coffee in a copper coffee pot."""),
    TongueTwisterItem("""The big black bug bit the big black bear and the big black bear bled blue black blood."""),
    TongueTwisterItem(
        """One smart fellow, he felt smart.
        Two smart fellows, they felt smart.
        Three smart fellows, they all felt smart."""
            .trimMargin(),
    ),
    TongueTwisterItem(
        """I am a mother pheasant plucker.
           I pluck mother pheasants.
           I am the most pleasant mother pheasant plucker,
           to ever pluck a mother pheasant.
        """.trimMargin(),
    ),
    TongueTwisterItem(
        """I'm not the fig plucker but the fig plucker's son and I'll pluck figs till the fig plucker comes.
        """.trimMargin(),
    ),
    TongueTwisterItem(
        """Sally sells seashells by the seashore, The seashells she sells, are seashells I’m sure.""",
        "digraphs of sh and ch",
    ),
    TongueTwisterItem(
        """The rain in Spain stays mainly in the plain.
           In Hertford, Hereford, and Hampshire, hurricanes hardly ever happen.
        """
            .trimMargin(),
        "From My Fair Lady",
    ),
)
