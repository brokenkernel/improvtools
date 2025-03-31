package com.brokenkernel.improvtools

import com.brokenkernel.improvtools.suggestionGenerator.data.repository.AudienceSuggestionDatumRepository
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.ResourcesAudienceSuggestionDatumRepository

interface AppContainer {
    val audienceSugestionDatumRespository: AudienceSuggestionDatumRepository
}

class DefaultAppContainer: AppContainer {
    override val audienceSugestionDatumRespository: AudienceSuggestionDatumRepository by lazy {
        ResourcesAudienceSuggestionDatumRepository()
    }
}