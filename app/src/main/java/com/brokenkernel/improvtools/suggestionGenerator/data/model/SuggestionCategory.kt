package com.brokenkernel.improvtools.suggestionGenerator.data.model

internal enum class SuggestionCategory(
    val title: String,
) {
    NOUN("Noun"),
    VERB("Verb"),
    ADJECTIVE("Adjective"),
    WORD("Word"),
    EMOTION("Emotion"),
    LOCATION("Location"),
    JOB("Job"),
    RELATION("Relation"),
    SITUATION("Situation"),
    GENRE("Genere"),
    DIRECTOR("Director"),
    PLAYWRITE("Playwrite"),
    HARDCONVO("Hard Conversation"),
}