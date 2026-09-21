package com.brokenkernel.improvtools.suggestions.repository

import android.content.res.Resources
import com.brokenkernel.improvtools.android.R
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
// TODO: internal
public class NamesAudienceSuggestionDatumRepository(
    resources: Resources,
) : AudienceSuggestionDatumRepository {
    private val audienceDatumParsed: AudienceSuggestionDatumODS?

    init {
        val unprocessedAudienceDatum: InputStream = resources.openRawResource(
            R.raw.names_suggestion_datum,
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
