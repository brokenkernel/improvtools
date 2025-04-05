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

    override fun getAudienceDatumForCategory(category: SuggestionCategory): Set<String> {
        if (audienceDatumParsed == null) {
            // This should never happen but we can't assert it.
            // TODO: It will also currently result in error on read since the Set is empty but that's a future bug to fix
            return setOf()
        }
        return audienceDatumParsed.categories[category.toString()].orEmpty()
    }
}