package com.brokenkernel.improvtools.encyclopaedia.data.model

public enum class GamesDatumTag(public val label: String, public val description: String? = null) {
    // TODO: i18n
    DIVERGENCE("#divergence", """Exercises that help you come up with new ideas"""),
    CONVERGENCE("#convergence", """Exercises that help the group get on the same wavelength"""),
    ENERGY("#energy"),
    NAMES("#names", """Help actors learn the names of people in the group"""),
    RANGE("#range", """Help expand the range of characters or situations one can play"""),
    VOCAL("#vocal"),
    STATUS("#status"),
    SHORTFORM("#shortform", """A singular game performed"""),
    MEDIUMFORM(
        "#mediumform",
        """A collection of scenes with a semi-coherent theme along a defined form or structure.
            | Sometimes also called <i>longform</i>
        """.trimMargin(),
    ),
    LONGFORM("#longform", """A full length performance intended to have a coherent and singular 'plot'"""),
    BREATH("#breath"),
    ;

    public companion object {
        public fun fromLabel(label: String): GamesDatumTag? {
            return entries.firstOrNull { gdt -> gdt.label == label }
        }
    }
}
