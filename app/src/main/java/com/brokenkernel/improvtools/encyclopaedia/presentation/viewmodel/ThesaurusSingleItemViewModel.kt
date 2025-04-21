package com.brokenkernel.improvtools.encyclopaedia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.encyclopaedia.data.model.ActionsThesaurus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// TODO: you shouldn't have to pass the word, perhaps assisted inject again?
@HiltViewModel
class ThesaurusSingleItemViewModel @Inject constructor() : ViewModel() {
    fun synonymsForWord(word: String): Iterable<String> {
        return ActionsThesaurus.synonymsForWord(word).sorted()
    }
}
