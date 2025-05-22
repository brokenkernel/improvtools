package com.brokenkernel.improvtools.encyclopaedia.data

public interface WordInfo {
    public val lemma: String // for debugging really

    // TODO add PoS
    public val description: String
    public val example: String
    public val synonyms: Set<String>
}
