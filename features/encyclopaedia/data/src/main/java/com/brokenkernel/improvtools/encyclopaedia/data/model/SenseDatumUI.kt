package com.brokenkernel.improvtools.encyclopaedia.data.model

public data class SenseDatumUI(
    val lemma: String, // for debugging really
    // TODO add PoS
    val description: String,
    val example: String,
    val synonyms: List<String>,
)
