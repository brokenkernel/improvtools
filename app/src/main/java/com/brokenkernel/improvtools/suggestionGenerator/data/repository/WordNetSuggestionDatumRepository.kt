package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import android.content.Context
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.encyclopaedia.data.WordType
import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import com.brokenkernel.improvtools.suggestions.data.storage.IdeaCategoryODS
import com.brokenkernel.improvtools.suggestions.data.storage.IdeaItemODS
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableSet

internal class WordNetSuggestionDatumRepository @Inject constructor(
    @param:ApplicationContext private val context: Context,
    val thesaurusRepository: ThesaurusRepository,
) : AudienceSuggestionDatumRepository {
    override fun getIdeaCategories(): List<IdeaCategoryODS> {
        //
        val d = thesaurusRepository.getDictionaryInfo()
        val nounList: ImmutableSet<String> = d.getWordsByType(WordType.NOUN)
        val verbList: ImmutableSet<String> = d.getWordsByType(WordType.VERB)
        val adjectiveList: ImmutableSet<String> = d.getWordsByType(WordType.ADJECTIVE)
        val adverbList: ImmutableSet<String> = d.getWordsByType(WordType.ADVERB)
        return listOf(
            IdeaCategoryODS(
                title = context.getString(R.string.suggestions_noun),
                showLinkToEmotion = false,
                ideas = nounList.map { word -> IdeaItemODS(idea = word) }.toSet(),
            ),

            IdeaCategoryODS(
                title = context.getString(R.string.suggestions_verb),
                showLinkToEmotion = false,
                ideas = verbList.map { word -> IdeaItemODS(idea = word) }.toSet(),
            ),

            IdeaCategoryODS(
                title = context.getString(R.string.suggestions_adjective),
                showLinkToEmotion = false,
                ideas = adjectiveList.map { word -> IdeaItemODS(idea = word) }.toSet(),
            ),

            IdeaCategoryODS(
                title = context.getString(R.string.suggestions_adverb),
                showLinkToEmotion = false,
                ideas = adverbList.map { word -> IdeaItemODS(idea = word) }.toSet(),
            ),
        )
    }
}
