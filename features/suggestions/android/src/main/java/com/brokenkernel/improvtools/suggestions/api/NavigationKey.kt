package com.brokenkernel.improvtools.suggestions.api

import com.brokenkernel.improvtools.coreinfra.ImprovToolsNavigationKey
import kotlinx.collections.immutable.ImmutableList
import kotlinx.parcelize.Parcelize

@Parcelize
public object SuggestionsScreenNavigationKey : ImprovToolsNavigationKey

// All Words is too big. Figure out better way to deal with this.
@Parcelize
public data class SuggestionsAllWordsForNavigationKey(
    val category: String,
) : ImprovToolsNavigationKey

@Parcelize
public data class SuggestionsExplanationNavigationKey(
    val word: String,
    val explanation: String,
) : ImprovToolsNavigationKey
