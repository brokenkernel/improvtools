package com.brokenkernel.improvtools

import android.content.res.Resources
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.AudienceSuggestionDatumRepository
import com.brokenkernel.improvtools.suggestionGenerator.data.repository.ResourcesAudienceSuggestionDatumRepository

internal interface AppContainer {
    val audienceSugestionDatumRespository: AudienceSuggestionDatumRepository
}

internal class DefaultAppContainer(resources: Resources) : AppContainer {

    override val audienceSugestionDatumRespository: AudienceSuggestionDatumRepository by lazy {
        ResourcesAudienceSuggestionDatumRepository(resources)
    }
}