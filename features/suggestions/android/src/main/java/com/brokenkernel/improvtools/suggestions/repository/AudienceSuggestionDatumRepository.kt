package com.brokenkernel.improvtools.suggestions.repository

import com.brokenkernel.improvtools.suggestions.data.storage.IdeaCategoryODS

// TODO: internal
public interface AudienceSuggestionDatumRepository {
    // TODO: internal
    public fun getIdeaCategories(): List<IdeaCategoryODS>
}
