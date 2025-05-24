package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import android.content.res.Resources
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.suggestions.data.storage.AudienceSuggestionDatumODS
import com.brokenkernel.improvtools.suggestions.data.storage.IdeaCategoryODS
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import java.io.InputStream
import java.io.InputStreamReader
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig

@OptIn(ExperimentalSerializationApi::class)
internal class ResourcesAudienceSuggestionDatumRepository(
    resources: Resources,
) : AudienceSuggestionDatumRepository {
    private val audienceDatumParsed: AudienceSuggestionDatumODS?

    init {
        val unprocessedAudienceDatum: InputStream = resources.openRawResource(
            R.raw.audience_suggestion_datum,
        )
        val irs = InputStreamReader(unprocessedAudienceDatum)
        val conf: Config = ConfigFactory.parseReader(irs)
        audienceDatumParsed =
            Hocon.decodeFromConfig<AudienceSuggestionDatumODS>(conf)
    }

    override fun getIdeaCategories(): List<IdeaCategoryODS> {
        return audienceDatumParsed?.categories.orEmpty()
    }
}
