package com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.encyclopaedia.api.ThesaurusAPI
import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ThesaurusSingleItemViewModel @Inject constructor(
    val thesaurusRepository: ThesaurusRepository,
    val thesaurusAPI: ThesaurusAPI, // todo: this should be accessed through a repository
) : ViewModel() {

    fun shouldShowActionSynonyms(word: String): Boolean {
        return thesaurusRepository.getActionsThesaurus().synonymsForWord(word).isNotEmpty()
    }

    fun synonyms(word: String): Iterable<String> {
        return thesaurusRepository.getActionsThesaurus().synonymsForWord(word).sorted()
    }

    // TODO: consider using Room in general for caching?
    fun renderedActionSynonyms(word: String): String {
        return buildString {
            append("<ul>")
            synonyms(word).forEach { synonym ->
                append("<li>$synonym</li>")
            }
            append("</ul>")
        }
    }

    fun renderedWordSenses(word: String): String {
        val allSenseDatum = thesaurusAPI.getSenseDatum(word)

        val senseString: String = buildString {
            allSenseDatum.forEach { pos, senseDatum ->
                append("<h2>$pos</h2>")
                append("<ul>")
                senseDatum.forEach { sense ->
                    append("<li>")
                    append("<u>${sense.description}</u>")
                    append("<i>${sense.example}</i>")
                    append("<ul>")
                    sense.synonyms.forEach { synonym ->
                        append("<li>$synonym</li>")
                    }
                    append("</ul>")
                    append("</li>")
                }
                append("</ul>")
            }
        }

        return senseString
    }
}
