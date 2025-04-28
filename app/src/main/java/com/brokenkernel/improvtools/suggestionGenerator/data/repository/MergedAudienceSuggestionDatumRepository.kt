package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaCategory
import javax.inject.Inject

// TODO: settings, enable/disable specific sources
internal class MergedAudienceSuggestionDatumRepository @Inject constructor(
    private val thesaurusAudienceSuggestionDatumRepository: ThesaurusAudienceSuggestionDatumRepository,
    private val resourcesAudienceSuggestionDatumRepository: ResourcesAudienceSuggestionDatumRepository,
    private val wordNetSuggestionDatumRepository: WordNetSuggestionDatumRepository,
) : AudienceSuggestionDatumRepository {
    override fun getIdeaCategories(): List<IdeaCategory> {
        return wordNetSuggestionDatumRepository.getIdeaCategories() +
            resourcesAudienceSuggestionDatumRepository.getIdeaCategories() +
            thesaurusAudienceSuggestionDatumRepository.getIdeaCategories()
    }
}
