package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import com.brokenkernel.improvtools.suggestions.data.storage.IdeaCategoryODS

internal interface AudienceSuggestionDatumRepository {
    fun getIdeaCategories(): List<IdeaCategoryODS>
}
