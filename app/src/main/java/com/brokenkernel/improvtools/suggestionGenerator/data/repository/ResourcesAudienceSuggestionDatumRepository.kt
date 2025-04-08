package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import android.content.res.Resources
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.suggestionGenerator.data.model.SuggestionCategory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream


@OptIn(ExperimentalSerializationApi::class)
internal class ResourcesAudienceSuggestionDatumRepository(
    resources: Resources,
) : AudienceSuggestionDatumRepository {
    private val audienceDatumParsed: AudienceSuggestionDatum?

    init {

        val unprocessedAudienceDatum: InputStream = resources.openRawResource(R.raw.audience_suggestion_datum)
        audienceDatumParsed =
            Json.decodeFromStream<AudienceSuggestionDatum>(unprocessedAudienceDatum)
    }

    override fun getIdeaCategories(): List<IdeaCategory> {
        return audienceDatumParsed?.categories.orEmpty();
    }
}