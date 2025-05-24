package com.brokenkernel.improvtools.encyclopaedia.data

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

public data class ActionItem(
    val word: String,
    val synonyms: ImmutableSet<String> = persistentSetOf(),
)
