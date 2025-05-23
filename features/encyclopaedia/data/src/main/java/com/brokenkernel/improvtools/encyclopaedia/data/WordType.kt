package com.brokenkernel.improvtools.encyclopaedia.data

import net.sf.extjwnl.data.POS

public enum class WordType(internal val internalPOS: POS) {
    NOUN(POS.NOUN),
    VERB(POS.VERB),
    ADVERB(POS.ADVERB),
    ADJECTIVE(POS.ADJECTIVE),
}
