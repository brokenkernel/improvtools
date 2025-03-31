package com.brokenkernel.improvtools.suggestionGenerator.data.model

// TODO: read ideas from resources/files/etc.
internal enum class SuggestionCategory(
    val title: String,
    val ideas: List<String>,
) {
    NOUN("Noun", listOf("Chair", "Fork")),
    VERB("Verb", listOf("Deduct", "Think")),
    ADJECTIVE("Adjective", listOf("Beautiful", "Smooth", "Heavy")),
    WORD("Word", listOf("Blueberry", "Life")),
    EMOTION("Emotion", listOf("Happy", "Sad")),
    LOCATION("Location", listOf("Mortuary", "Kitchen")),
    JOB("Job", listOf("Doctor", "Lawyer")),
    RELATION("Relation", listOf("husband/wife", "doctor/patient")),
    SITUATION("Situation", listOf("Playing a practical joke", "Having a Babys")),
    GENRE("Genere", listOf("Horror", "Action")),
    DIRECTOR("Director", listOf("David Lynch", "No idea")),
    PLAYWRITE("Playwrite", listOf("Shakespere", "Nowel Coward", "Diana Son")),
    HARDCONVO("Hard Conversation", listOf("Firing Someone", "Having Cancer")),
}