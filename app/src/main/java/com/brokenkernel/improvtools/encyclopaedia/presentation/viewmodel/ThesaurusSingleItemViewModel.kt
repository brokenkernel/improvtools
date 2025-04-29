package com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.encyclopaedia.api.ThesaurusAPI
import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = ThesaurusSingleItemViewModel.Factory::class)
internal class ThesaurusSingleItemViewModel @AssistedInject constructor(
    @Assisted("word") val word: String,
    val thesaurusRepository: ThesaurusRepository,
    val thesaurusAPI: ThesaurusAPI, // todo: this should be accessed through a repository
) : ViewModel() {
    fun synonyms(): Iterable<String> {
        return thesaurusRepository.getActionsThesaurus().synonymsForWord(word).sorted()
    }

    fun getWordSensesFullyRenderedString(): String? {
        val allSenseDatum = thesaurusAPI.getSenseDatum(word)
        if (allSenseDatum == null) {
            return null
        }

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

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("word") word: String,
        ): ThesaurusSingleItemViewModel
    }
}
