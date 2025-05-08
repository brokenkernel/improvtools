package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import android.content.Context
import com.brokenkernel.improvtools.R
import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaCategoryODS
import com.brokenkernel.improvtools.suggestionGenerator.data.model.IdeaItemODS
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import net.sf.extjwnl.data.IndexWord
import net.sf.extjwnl.data.POS
import net.sf.extjwnl.dictionary.Dictionary

internal class WordNetSuggestionDatumRepository @Inject constructor(
    @param:ApplicationContext private val context: Context,
    val thesaurusRepository: ThesaurusRepository,
) : AudienceSuggestionDatumRepository {
    override fun getIdeaCategories(): List<IdeaCategoryODS> {
        //
        val d: Dictionary? = thesaurusRepository.getEXTJWNLDictionary()
        if (d == null) {
            return emptyList()
        }
        val nounList: List<IndexWord?> = d.getIndexWordIterator(POS.NOUN).asSequence().toList()
        val verbList: List<IndexWord?> = d.getIndexWordIterator(POS.VERB).asSequence().toList()
        val adjectiveList: List<IndexWord?> = d.getIndexWordIterator(POS.ADJECTIVE).asSequence().toList()
        val adverbList: List<IndexWord?> = d.getIndexWordIterator(POS.ADVERB).asSequence().toList()
        return listOf(
            IdeaCategoryODS(
                title = context.getString(R.string.suggestions_noun),
                showLinkToEmotion = false,
                ideas = nounList.map { word -> IdeaItemODS(idea = word?.lemma.toString()) }.toSet(),
            ),

            IdeaCategoryODS(
                title = context.getString(R.string.suggestions_verb),
                showLinkToEmotion = false,
                ideas = verbList.map { word -> IdeaItemODS(idea = word?.lemma.toString()) }.toSet(),
            ),

            IdeaCategoryODS(
                title = context.getString(R.string.suggestions_adjective),
                showLinkToEmotion = false,
                ideas = adjectiveList.map { word -> IdeaItemODS(idea = word?.lemma.toString()) }.toSet(),
            ),

            IdeaCategoryODS(
                title = context.getString(R.string.suggestions_adverb),
                showLinkToEmotion = false,
                ideas = adverbList.map { word -> IdeaItemODS(idea = word?.lemma.toString()) }.toSet(),
            ),
        )
    }
}
