package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import android.content.res.Resources
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionCategory
import com.fasterxml.jackson.core.JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION
import com.fasterxml.jackson.core.JsonParser.Feature.STRICT_DUPLICATE_DETECTION
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.InputStream

internal interface AudienceSuggestionDatumRepository {
    fun getAudienceDatumForCategory(category: SuggestionCategory): Set<String>
}

private data class AudienceSuggestionDatum(
    val categories: Map<String, Set<String>>,
)

internal class ResourcesAudienceSuggestionDatumRepository(
    resources: Resources,
) : AudienceSuggestionDatumRepository {
    private val audienceDatumParsed: AudienceSuggestionDatum?

    init {
        val unprocessedAudienceDatum: InputStream = resources.openRawResource(R.raw.audience_suggestion_datum)
        val mapper = jacksonObjectMapper()
        mapper.configure(
            INCLUDE_SOURCE_IN_LOCATION, true
        )
        mapper.configure(
            STRICT_DUPLICATE_DETECTION, true
        )

        val audienceDatumParsedMaybe: AudienceSuggestionDatum? = mapper.readValue<AudienceSuggestionDatum>(
            unprocessedAudienceDatum,
            AudienceSuggestionDatum::class.java
        )
        audienceDatumParsed = audienceDatumParsedMaybe
    }

    override fun getAudienceDatumForCategory(category: SuggestionCategory): Set<String> {
        if (audienceDatumParsed == null) {
            // This should never happen but we can't assert it.
            // It will also currently result in error on read since the Set is empty but that's a future bug to fix
            return setOf()
        }
        return audienceDatumParsed.categories[category.toString()].orEmpty()
    }
}