package com.brokenkernel.improvtools.encyclopaedia.data.model

data class ActionItem(
    val word: String,
    val synonyms: Set<String> = setOf(),
)
