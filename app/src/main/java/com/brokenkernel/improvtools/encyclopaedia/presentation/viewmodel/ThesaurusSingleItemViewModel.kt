package com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.encyclopaedia.data.repository.ThesaurusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// TODO: you shouldn't have to pass the word, perhaps assisted inject again?
@HiltViewModel
internal class ThesaurusSingleItemViewModel @Inject constructor(
    val thesaurusRepository: ThesaurusRepository,
) : ViewModel() {
    fun synonymsForWord(word: String): Iterable<String> {
        return thesaurusRepository.getActionsThesaurus().synonymsForWord(word).sorted()
    }
}
