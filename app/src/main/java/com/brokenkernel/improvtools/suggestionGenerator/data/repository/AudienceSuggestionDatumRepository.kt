package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaCategoryODS

internal interface AudienceSuggestionDatumRepository {
    fun getIdeaCategories(): List<IdeaCategoryODS>
}
