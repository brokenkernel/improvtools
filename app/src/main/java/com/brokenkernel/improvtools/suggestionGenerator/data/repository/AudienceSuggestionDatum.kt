package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaCategory

internal interface AudienceSuggestionDatumRepository {
    fun getIdeaCategories(): List<IdeaCategory>
}
