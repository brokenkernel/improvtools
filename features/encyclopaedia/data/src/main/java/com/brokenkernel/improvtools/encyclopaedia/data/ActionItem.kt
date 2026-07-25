package com.brokenkernel.improvtools.encyclopaedia.data

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

public data class ActionItem(
    val word: String,
    val synonyms: ImmutableSet<String> = persistentSetOf(),

    /**
     * The word is best used when played to a group of people
     */
    val plural: Boolean = false
)
