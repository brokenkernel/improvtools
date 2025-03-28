package com.brokenkernel.improvtools.suggestionGenerator.data.model

internal enum class SuggestionCategory(
    val title: String,
    val ideas: List<String>,
) {
    NOUN("Noun", listOf("Chair", "Fork")),
    VERB("Verb", listOf("Deduct", "Think")),
    WORD("Word", listOf("Blueberry", "Life")),
    EMOTION("Emotion", listOf("Happy", "Sad")),
    LOCATION("Location", listOf("Mortuary", "Kitchen")),
    JOB("Job", listOf("Doctor", "Lawyer")),
}