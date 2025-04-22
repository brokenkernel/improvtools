package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaCategory
import javax.inject.Inject

internal class MergedAudienceSuggestionDatumRepository @Inject constructor(
    private val thesaurusAudienceSuggestionDatumRepository: ThesaurusAudienceSuggestionDatumRepository,
    private val resourcesAudienceSuggestionDatumRepository: ResourcesAudienceSuggestionDatumRepository,
) : AudienceSuggestionDatumRepository {
    override fun getIdeaCategories(): List<IdeaCategory> {
        return resourcesAudienceSuggestionDatumRepository.getIdeaCategories() +
            thesaurusAudienceSuggestionDatumRepository.getIdeaCategories()
    }
}
