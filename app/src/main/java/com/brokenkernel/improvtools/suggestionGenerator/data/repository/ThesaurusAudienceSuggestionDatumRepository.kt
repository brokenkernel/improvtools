package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import android.content.Context
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.encyclopaedia.api.ThesaurusAPI
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaCategoryODS
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaItemODS
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class ThesaurusAudienceSuggestionDatumRepository @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val thesaurusAPI: ThesaurusAPI,
) : AudienceSuggestionDatumRepository {
    override fun getIdeaCategories(): List<IdeaCategoryODS> {
        return listOf(
            IdeaCategoryODS(
                title = context.resources.getString(R.string.action_word),
                showLinkToEmotion = false,
                ideas = thesaurusAPI
                    .getActionWords()
                    .map { word -> IdeaItemODS(idea = word) }
                    .toSet(),
            ),
        )
    }
}
