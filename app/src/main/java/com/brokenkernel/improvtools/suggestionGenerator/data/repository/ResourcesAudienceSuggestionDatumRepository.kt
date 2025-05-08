package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import android.content.res.Resources
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.suggestionGenerator.data.model.AudienceSuggestionDatumODS
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaCategoryODS
import java.io.InputStream
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@OptIn(ExperimentalSerializationApi::class)
internal class ResourcesAudienceSuggestionDatumRepository(
    resources: Resources,
) : AudienceSuggestionDatumRepository {
    private val audienceDatumParsed: AudienceSuggestionDatumODS?

    init {
        val unprocessedAudienceDatum: InputStream = resources.openRawResource(
            R.raw.audience_suggestion_datum,
        )
        audienceDatumParsed =
            Json.decodeFromStream<AudienceSuggestionDatumODS>(unprocessedAudienceDatum)
    }

    override fun getIdeaCategories(): List<IdeaCategoryODS> {
        return audienceDatumParsed?.categories.orEmpty()
    }
}
