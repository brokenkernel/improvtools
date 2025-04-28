package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import android.content.Context
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaCategory
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import net.sf.extjwnl.data.IndexWord
import net.sf.extjwnl.data.POS
import net.sf.extjwnl.dictionary.Dictionary

internal class WordNetSuggestionDatumRepository @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : AudienceSuggestionDatumRepository {
    override fun getIdeaCategories(): List<IdeaCategory> {
        //
        val d: Dictionary? = Dictionary.getDefaultResourceInstance()
        if (d == null) {
            return emptyList()
        }
        val nounList: List<IndexWord?> = d.getIndexWordIterator(POS.NOUN).asSequence().toList()
        val verbList: List<IndexWord?> = d.getIndexWordIterator(POS.VERB).asSequence().toList()
        val adjectiveList: List<IndexWord?> = d.getIndexWordIterator(POS.ADJECTIVE).asSequence().toList()
        val adverbList: List<IndexWord?> = d.getIndexWordIterator(POS.ADVERB).asSequence().toList()
        return listOf(
            IdeaCategory(
                title = context.getString(R.string.suggestions_noun),
                showLinkToEmotion = false,
                ideas = nounList.map { word -> IdeaItem(idea = word?.lemma.toString()) }.toSet(),
            ),

            IdeaCategory(
                title = context.getString(R.string.suggestions_verb),
                showLinkToEmotion = false,
                ideas = verbList.map { word -> IdeaItem(idea = word?.lemma.toString()) }.toSet(),
            ),

            IdeaCategory(
                title = context.getString(R.string.suggestions_adjective),
                showLinkToEmotion = false,
                ideas = adjectiveList.map { word -> IdeaItem(idea = word?.lemma.toString()) }.toSet(),
            ),

            IdeaCategory(
                title = context.getString(R.string.suggestions_adverb),
                showLinkToEmotion = false,
                ideas = adverbList.map { word -> IdeaItem(idea = word?.lemma.toString()) }.toSet(),
            ),
        )
    }
}
