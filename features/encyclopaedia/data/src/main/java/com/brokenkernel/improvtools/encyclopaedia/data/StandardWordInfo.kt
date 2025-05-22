package com.brokenkernel.improvtools.encyclopaedia.data

public data class StandardWordInfo(
    override val lemma: String,
    override val description: String,
    override val example: String,
    override val synonyms: Set<String>,
) : WordInfo
