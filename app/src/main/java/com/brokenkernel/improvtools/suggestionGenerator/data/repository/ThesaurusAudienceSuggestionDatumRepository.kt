package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import android.content.Context
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.encyclopaedia.data.DictionaryInfo
import com.brokenkernel.improvtools.encyclopaedia.data.WordType
import com.brokenkernel.improvtools.suggestions.data.storage.IdeaCategoryODS
import com.brokenkernel.improvtools.suggestions.data.storage.IdeaItemODS
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class ThesaurusAudienceSuggestionDatumRepository @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val dictionaryInfo: DictionaryInfo,
) : AudienceSuggestionDatumRepository {
    override fun getIdeaCategories(): List<IdeaCategoryODS> {
        return listOf(
            IdeaCategoryODS(
                title = context.resources.getString(R.string.action_word),
                showLinkToEmotion = false,
                ideas = dictionaryInfo.getWordsByType(WordType.ACTION)
                    .map { word -> IdeaItemODS(idea = word) }
                    .toSet(),
            ),
        )
    }
}
