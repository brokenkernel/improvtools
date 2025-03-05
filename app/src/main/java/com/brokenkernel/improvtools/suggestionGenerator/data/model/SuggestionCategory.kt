package com.brokenkernel.improvtools.suggestionGenerator.data.model

data class SuggestionCategory(
    val title: String,
    val ideas: List<String>,
)

object SuggestionDatum {
    private val noun: SuggestionCategory = SuggestionCategory(
        "Noun",
        listOf("Chair", "Fork"),
    )
    private val verb: SuggestionCategory = SuggestionCategory(
        "verb",
        listOf("Deduct", "Think"),
    )
    val allCategories: List<SuggestionCategory> = listOf(noun, verb)
}